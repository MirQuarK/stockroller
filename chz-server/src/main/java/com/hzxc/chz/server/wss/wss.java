package com.hzxc.chz.server.wss;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServletComponentScan
@ServerEndpoint(value = "/websocket/{param}", configurator = MyEndpointConfigure.class)
@Component
public class wss {

    @Autowired
    RedisTemplate redisTemplate;

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<wss> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    public static Map<String, Map<wss, Session>> mapStockUser = new HashMap<>();
    public static Map<Session, List<String>> mapUserStock = new HashMap<>();
    public static Map<String, Session> mapUserSession = new HashMap<>();

    /**
     * 连接建立成功调用的方法
     * */
    @OnOpen
    public void onOpen(@PathParam(value="param") String param, Session session) {
        String rkey = "spring:session:sessions:" + param;

        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1

        if(!redisTemplate.hasKey(rkey)) {
            try {
                sendMessage("not login");
                session.close();
            } catch (IOException e) {
                System.out.println("IO异常");
            }
            return;
        }

        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            sendMessage("");
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1

        // del stock wss
        for(String stock : mapStockUser.keySet()) {
            Map<wss, Session> m = mapStockUser.get(stock);
            m.remove(this);
        }

        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);

        JSONObject jobj = (JSONObject) new JSONObject().parse(message);
        String stock = jobj.getString("stock");
        String cmd = jobj.getString("cmd");
        Map<wss, Session> ls = mapStockUser.get(stock);
        if(ls == null) ls = new HashMap<>();
        if(cmd.equalsIgnoreCase("add")) {
            ls.put(this, session);
        } else if(cmd.equalsIgnoreCase("del")) {
            ls.remove(session);
        }
    }

     @OnError
     public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
     }

     public void sendMessage(String message) throws IOException {
         this.session.getBasicRemote().sendText(message);
         //this.session.getAsyncRemote().sendText(message);
     }

    // send stock info to all needed user
     public static void SendStockInfo(String stock, String msg) {
         Map<wss, Session> ms = mapStockUser.get(stock);
         if(ms == null) {
             ms = new HashMap<>();
         }

         for(wss s: ms.keySet()) {
             try {
                 s.sendMessage(msg);
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        wss.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        wss.onlineCount--;
    }
}
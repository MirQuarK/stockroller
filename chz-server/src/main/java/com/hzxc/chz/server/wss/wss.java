package com.hzxc.chz.server.wss;

import org.springframework.beans.factory.annotation.Autowired;
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

@ServerEndpoint(value = "/websocket")
@Component
public class wss {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<wss> webSocketSet = new CopyOnWriteArraySet<wss>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    public static Map<String, List<Session>> mapStockUser = new HashMap<>();
    public static Map<Session, List<String>> mapUserStock = new HashMap<>();
    public static Map<String, Session> mapUserSession = new HashMap<>();

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 连接建立成功调用的方法
     * */
    @OnOpen
    public void onOpen(@PathParam(value="param") String param, Session session) {

        if(!redisTemplate.hasKey("spring:session:sessions:" + param)) {
            try {
                sendMessage("not login");
                session.close();
            } catch (IOException e) {
                System.out.println("IO异常");
            }
            return;
        }

        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            sendMessage("");
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);

        //群发消息
        for (wss item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     * */
     @OnError
     public void onError(Session session, Throwable error) {
     System.out.println("发生错误");
     error.printStackTrace();
     }

     public void sendMessage(String message) throws IOException {
         this.session.getBasicRemote().sendText(message);
         //this.session.getAsyncRemote().sendText(message);
     }

     /**
      * 群发自定义消息, 我们不要群发，客户的自选股都是独立的。
      * */
    public static void sendInfo(String message) throws IOException {
        for (wss item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
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
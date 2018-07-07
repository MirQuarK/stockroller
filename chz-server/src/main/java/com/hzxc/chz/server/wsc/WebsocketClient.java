package com.hzxc.chz.server.wsc;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by cz on 2017/3/31.
 */
@Service
public class WebsocketClient implements WebsocketClientInter{
    public static WebSocketClient client = null;
    @PostConstruct
    @Override
    public void init() {
        try {
            client = new WebSocketClient(new URI("ws://114.55.150.38:8008/"),new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("打开链接");
                    handlerOpen();
                }

                @Override
                public void onMessage(String s) {
                    handlerResp(s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("链接已关闭");
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    System.out.println("发生错误已关闭");
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        client.connect();
        System.out.println(client.getDraft());

        return;
    }

    public String buildXmlRequest(String com, Map<String, String> param) {
        String strRet = "<" + com;
        for(String key : param.keySet()) {
            strRet += " " + key + "=\"" + param.get(key) + "\"";
        }
        strRet += " />\n\n";
        return strRet;
    }

    public void close() {
        client.close();
    }

    public static void send(String cmd) {
        client.send(cmd);
    }

    @Override
    public  void handlerOpen () {
        return;
    }

    @Override
    public void handlerResp (String msg) {
        return;
    }
}
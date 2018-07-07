package com.hzxc.chz.server.wsc;

public interface WebsocketClientInter {
    void handlerOpen();
    void handlerResp(String msg);
}

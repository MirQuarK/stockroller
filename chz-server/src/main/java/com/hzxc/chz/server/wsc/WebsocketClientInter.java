package com.hzxc.chz.server.wsc;

public interface WebsocketClientInter {
    void init();
    void handlerOpen();
    void handlerResp(String msg);
}

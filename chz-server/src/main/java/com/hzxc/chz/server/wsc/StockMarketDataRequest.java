package com.hzxc.chz.server.wsc;

public class StockMarketDataRequest {
    public static void getMacketData(String stockId) {
        String req = "<SubscribeMarketData RequestID=\"1\"><item InstrumentID=\""+stockId+".SH\"  E=\"SSE\" /></SubscribeMarketData>\n\n";
        StockMacketDataRequestClient.send(req);
    }
}

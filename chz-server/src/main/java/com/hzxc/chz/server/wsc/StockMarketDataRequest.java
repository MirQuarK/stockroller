package com.hzxc.chz.server.wsc;

// request data class
public class StockMarketDataRequest {
    public static void getMarcketData(String stockId) {
        String req = "<SubscribeMarketData RequestID=\"1\"><item InstrumentID=\""+stockId+".SH\"  E=\"SSE\" /></SubscribeMarketData>\n\n";
        StockMacketWsClient.send(req);
    }
}

package com.hzxc.chz.server.wsc;

// request data class
public class StockMarketDataRequest {
    public static void getMarcketData(String stockId) {
        String req = "<SubscribeMarketData RequestID=\"1\"><item InstrumentID=\""+stockId+".SH\"  E=\"SSE\" /></SubscribeMarketData>\n\n";
        StockMacketWsClient.send(req);
    }

    public static void unGetMarcketData(String stockId) {
        String req = "<UnSubscribeMarketData RequestID=\"1\"><item InstrumentID=\""+stockId+".SH\"  E=\"SSE\" /></UnSubscribeMarketData>\n\n";
        StockMacketWsClient.send(req);
    }

}

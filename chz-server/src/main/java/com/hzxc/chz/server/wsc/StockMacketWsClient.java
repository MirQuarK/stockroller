package com.hzxc.chz.server.wsc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hzxc.chz.server.wss.wss;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class StockMacketWsClient extends WebsocketClient {

    private static final Log logger = LogFactory.getLog(StockMacketWsClient.class);

    @Override
    public void handlerOpen() {
        try {
            client.send("SS_MARKET_CLIENT\n\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    @Override
    public void handlerResp(String msg) {
        System.out.println("收到消息"+msg);
        if(msg.equalsIgnoreCase("SS_COUNTER_SERVER\n\n")) {
            client.send("<ReqUserLogin BrokerID=\"FULL\"  SubscribeInstStatus=\"1\"  Version=\"101\" />\n\n");
            return;
        } else if (msg.equalsIgnoreCase( "HEART_BEAT\n\n")) {
            return;
        } else if (msg.equalsIgnoreCase("HEART_NOTIFY\n\n")) {
            send("HEART_NOTIFY\n\n");
            return;
        }

        try {
            //创建DocumentBuilder对象
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(msg.getBytes("UTF-8"));
            Document document = db.parse(inputStream);
            String cmd = document.getFirstChild().getNodeName();
            callHandlerFunc(cmd, document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // reflect call back
    public static void callHandlerFunc(String cmd, Document document) {
        logger.debug("in call handlerFunc smc = " + cmd);
        String className="com.hzxc.chz.server.wsc.StockMacketWsClient"; //类名
        Class[] params = new Class[]{Document.class};//参数
        String methodName = cmd;//方法名
        Object obj = null;     //调用方法的对象, 静态对象为null
        Object[] args = new Object[]{document};  //调用方法传的参数
        try {
            Object returnObj = Class.forName(className).getMethod(methodName, params).invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 登陆成功回调
    public static void OnRspUserLogin(Document document){
        // test ask stock data.
        StockMarketDataRequest.getMarcketData("600519");
    }

    public static void OnRspUnSubMarketData(Document documenit) {

    }

    // 提交查询数据消息处理
    public static void OnRspSubMarketData(Document document) {

    }

    // 获取深度行情数据消息处理.
    // TODO 保存缓存.给wss用
    public static void OnRtnDepthMarketData(Document document) {
        try {
            NamedNodeMap aa = document.getFirstChild().getAttributes();
            Node nn = aa.getNamedItem("EI");
            String EI = nn.getNodeName();
            String vv = nn.getNodeValue();
            String[] st = vv.split("\\.");
            logger.info("in OnRtnDepthMarketData ei = " + EI);
            wss.SendStockInfo(st[0], JSON.toJSONString(document));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 合约交易状态通知回调
    public static void OnRtnInstrumentStatus(Document document) {
        try {
            NamedNodeMap aa = document.getFirstChild().getAttributes();
            Node nn = aa.getNamedItem("EI");
            String EI = nn.getNodeName();
            logger.info("in OnRtnInstrumentStatus ei = " + EI);
            //
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void OnRtnStockMarketData(Document document) {
        try {
            NamedNodeMap aa = document.getFirstChild().getAttributes();
            Node nn = aa.getNamedItem("EI");
            String EI = nn.getNodeName();
            logger.info("in OnRtnStockMarketData ei = " + EI);
            //
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    //深度行情通知
    M.p_OnRtnDepthMarketData = function (e) {
        var data = new Object();
        //交易日
        data.TradingDay = getDate(e, "TD");
        //合约代码
        data.InstrumentID = getString(e, "I");
        //交易所代码
        data.ExchangeID = getString(e, "E");
        //合约在交易所的代码
        data.ExchangeInstID = getString(e, "EI");
        //最新价
        data.LastPrice = getDoubleN(e, "P");
        //行权价
        data.StrikePrice = getDoubleN(e, "SK");
        //上次结算价
        data.PreSettlementPrice = getDoubleN(e, "PSP");
        //昨收盘
        data.PreClosePrice = getDoubleN(e, "PCP");
        //昨持仓量
        data.PreOpenInterest = getDouble(e, "POI");
        //今开盘
        data.OpenPrice = getDoubleN(e, "OP");
        //最高价
        data.HighestPrice = getDoubleN(e, "HP");
        //最低价
        data.LowestPrice = getDoubleN(e, "LP");
        //数量
        data.Volume = getDouble(e, "V");
        //成交金额
        data.Turnover = getDoubleN(e, "T");
        //持仓量
        data.OpenInterest = getDouble(e, "O");
        //今收盘
        data.ClosePrice = getDoubleN(e, "C");
        //本次结算价
        data.SettlementPrice = getDoubleN(e, "SP");
        //涨停板价
        data.UpperLimitPrice = getDoubleN(e, "UL");
        //跌停板价
        data.LowerLimitPrice = getDoubleN(e, "LL");
        //昨虚实度
        data.PreDelta = getDouble(e, "PD");
        //今虚实度
        data.CurrDelta = getDouble(e, "CD");
        //最后修改时间
        data.UpdateTime = getString(e, "UT");
        //最后修改毫秒
        data.UpdateMillisec = getInt(e, "UM");
        //当日均价
        data.AveragePrice = getDoubleN(e, "AVG");
        //业务日期
        data.ActionDay = getString(e, "AD");
        //深度行情列表
        data.Depthes = new Array();
        for (var i = 1; i <= 5; i++) {
            var d = new Object();
            data.Depthes.push(d);
            //申买价一
            d.BidPrice = getDoubleN(e, "BP" + i);
            //申买量一
            d.BidVolume = getDouble(e, "BV" + i);
            //申卖价一
            d.AskPrice = getDoubleN(e, "AP" + i);
            //申卖量一
            d.AskVolume = getDouble(e, "AV" + i);
        }
        //回调
        this.OnRtnMarketData(data);
    }

    //深度行情通知
    M.p_OnRtnStockMarketData = function (e) {
        var data = new Object();
        //交易日
        data.TradingDay = getDate(e, "TD");
        //合约代码
        data.InstrumentID = getString(e, "I");
        //交易所代码
        data.ExchangeID = getString(e, "E");
        //最新价
        data.LastPrice = getDoubleN(e, "P");
        //昨收盘
        data.PreClosePrice = getDoubleN(e, "PCP");
        //今开盘
        data.OpenPrice = getDoubleN(e, "OP");
        //最高价
        data.HighestPrice = getDoubleN(e, "HP");
        //最低价
        data.LowestPrice = getDoubleN(e, "LP");
        //数量
        data.Volume = getDouble(e, "V");
        //成交金额
        data.Turnover = getDoubleN(e, "T");
        //今收盘
        data.ClosePrice = getDoubleN(e, "C");
        //涨停板价
        data.UpperLimitPrice = getDoubleN(e, "UL");
        //跌停板价
        data.LowerLimitPrice = getDoubleN(e, "LL");
        //最后修改时间
        data.UpdateTime = getString(e, "UT");
        //最后修改毫秒
        data.UpdateMillisec = getInt(e, "UM");
        //业务日期
        data.ActionDay = getDate(e, "AD");
        //委托买入总量
        data.TotalBidVolume = getDouble(e, "BV");
        //委托卖出总量
        data.TotalAskVolume = getDouble(e, "AV");
        //加权平均委买价格
        data.WeightedAvgBidPrice = getDoubleN(e, "BP");
        //加权平均委卖价格
        data.WeightedAvgAskPrice = getDoubleN(e, "AP");
        //IPO估值
        data.IOPV = getDouble(e, "IP");
        //到期收益率
        data.YieldToMaturity = getDouble(e, "YM");
        //市盈率1
        data.PeRatio1 = getDouble(e, "P1");
        //市盈率2
        data.PeRatio2 = getDouble(e, "P2");
        //深度行情列表
        data.Depthes = new Array();
        //读取深度行情列表
        for (var de = e.firstChild; de; de = de.nextElementSibling) {
            var d = new Object();
            data.Depthes.push(d);
            d.AskPrice = getDoubleN(de, "A");
            d.BidPrice = getDoubleN(de, "B");
            d.AskVolume = getInt(de, "M");
            d.BidVolume = getInt(de, "N");
        }
        //回调
        this.OnRtnMarketData(data);
    }
*/
}

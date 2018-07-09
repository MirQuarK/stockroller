//创建XmlDoc
function ssGetXmlDoc(xmlstr) {
	if (window.DOMParser) {
		try {
			var domParser = new DOMParser();
			return domParser.parseFromString(xmlstr, "text/xml");
		} catch (e) { }
	}
	else if (!window.DOMParser && window.ActiveXObject) {
		var xmlDomVersions = ["MSXML2.DOMDocument", "Microsoft.XMLDOM"];
		for (var i = 0; i < xmlDomVersions.length; i++) {
			try {
				var xmlDoc = new ActiveXObject(xmlDomVersions[i]);
				xmlDoc.async = false;
				xmlDoc.loadXML(xmlstr);
				return xmlDoc;
			} catch (e) { continue; }
		}
	}
	else { throw "浏览器不支持XML"; }
}

//没有操作的空方法
function emptyMethod() { }

//一融资管行情接口
function ssMdApi(frontAddress) {
	this.frontAddress = frontAddress;
	this.socket = undefined;
	this.reconnectcount = 0;
	this.maxRequestID = 0;
	this.requests = new Array();

	//当客户端与交易后台建立起通信连接时（还未登录前），该方法被调用。
	this.OnFrontConnected = emptyMethod;
	//当客户端与交易后台通信连接断开时，该方法被调用。当发生这个情况后，API会自动重新连接，客户端可不做处理。
	//@param nReason 错误原因
	//        0x1001 网络读失败
	//        0x1002 网络写失败
	//        0x2001 接收心跳超时
	//        0x2002 发送心跳失败
	//        0x2003 收到错误报文
	this.OnFrontDisconnected = emptyMethod;
	//心跳超时警告。当长时间未收到报文时，该方法被调用。
	//@param nTimeLapse 距离上次接收报文的时间
	this.OnHeartBeatWarning = emptyMethod;
	//行情通知
	this.OnRtnMarketData = emptyMethod;
	//合约交易状态通知
	this.OnRtnInstrumentStatus = emptyMethod;
	//错误通知
	this.OnRspError = emptyMethod;
}

//一融资管交易接口
function ssTraderApi(frontAddress) {
	this.frontAddress = frontAddress;
	this.socket = undefined;
	this.reconnectcount = 0;
	this.maxRequestID = 0;
	this.publicTopicIndex = 0;
	this.privateTopicIndex = 0;

	//当客户端与交易后台建立起通信连接时（还未登录前），该方法被调用。
	this.OnFrontConnected = emptyMethod;
	//当客户端与交易后台通信连接断开时，该方法被调用。当发生这个情况后，API会自动重新连接，客户端可不做处理。
	//@param nReason 错误原因
	//        0x1001 网络读失败
	//        0x1002 网络写失败
	//        0x2001 接收心跳超时
	//        0x2002 发送心跳失败
	//        0x2003 收到错误报文
	this.OnFrontDisconnected = emptyMethod;
	//心跳超时警告。当长时间未收到报文时，该方法被调用。
	//@param nTimeLapse 距离上次接收报文的时间
	this.OnHeartBeatWarning = emptyMethod;
	//错误通知
	this.OnRspError = emptyMethod;
	//报单通知
	this.OnRtnOrder = emptyMethod;
	//成交通知
	this.OnRtnTrade = emptyMethod;
	//持仓自动化通知
	this.OnRtnPositionAutomation = emptyMethod;
	//账户风控信息通知
	this.OnRtnAccountRisk = emptyMethod;
	//合约风控信息通知
	this.OnRtnInstrumentRisk = emptyMethod;
	//报单录入错误回报
	this.OnErrRtnOrderInsert = emptyMethod;
	//报单操作错误回报
	this.OnErrRtnOrderAction = emptyMethod;
	//合约交易状态通知
	this.OnRtnInstrumentStatus = emptyMethod;
	//交易通知
	this.OnRtnTradingNotice = emptyMethod;
	//提示条件单校验错误
	this.OnRtnErrorConditionalOrder = emptyMethod;
	//执行宣告通知
	this.OnRtnExecOrder = emptyMethod;
	//执行宣告录入错误回报
	this.OnErrRtnExecOrderInsert = emptyMethod;
	//执行宣告操作错误回报
	this.OnErrRtnExecOrderAction = emptyMethod;
	//询价录入错误回报
	this.OnErrRtnForQuoteInsert = emptyMethod;
	//报价通知
	this.OnRtnQuote = emptyMethod;
	//报价录入错误回报
	this.OnErrRtnQuoteInsert = emptyMethod;
	//报价操作错误回报
	this.OnErrRtnQuoteAction = emptyMethod;
	//询价通知
	this.OnRtnForQuoteRsp = emptyMethod;
	//保证金监控中心用户令牌
	this.OnRtnCFMMCTradingAccountToken = emptyMethod;
	//批量报单操作错误回报
	this.OnErrRtnBatchOrderAction = emptyMethod;
	//申请组合通知
	this.OnRtnCombAction = emptyMethod;
	//申请组合录入错误回报
	this.OnErrRtnCombActionInsert = emptyMethod;
	//银行发起银行资金转期货通知
	this.OnRtnFromBankToFutureByBank = emptyMethod;
	//银行发起期货资金转银行通知
	this.OnRtnFromFutureToBankByBank = emptyMethod;
	//银行发起冲正银行转期货通知
	this.OnRtnRepealFromBankToFutureByBank = emptyMethod;
	//银行发起冲正期货转银行通知
	this.OnRtnRepealFromFutureToBankByBank = emptyMethod;
	//期货发起银行资金转期货通知
	this.OnRtnFromBankToFutureByFuture = emptyMethod;
	//期货发起期货资金转银行通知
	this.OnRtnFromFutureToBankByFuture = emptyMethod;
	//系统运行时期货端手工发起冲正银行转期货请求，银行处理完毕后报盘发回的通知
	this.OnRtnRepealFromBankToFutureByFutureManual = emptyMethod;
	//系统运行时期货端手工发起冲正期货转银行请求，银行处理完毕后报盘发回的通知
	this.OnRtnRepealFromFutureToBankByFutureManual = emptyMethod;
	//期货发起查询银行余额通知
	this.OnRtnQueryBankBalanceByFuture = emptyMethod;
	//期货发起银行资金转期货错误回报
	this.OnErrRtnBankToFutureByFuture = emptyMethod;
	//期货发起期货资金转银行错误回报
	this.OnErrRtnFutureToBankByFuture = emptyMethod;
	//系统运行时期货端手工发起冲正银行转期货错误回报
	this.OnErrRtnRepealBankToFutureByFutureManual = emptyMethod;
	//系统运行时期货端手工发起冲正期货转银行错误回报
	this.OnErrRtnRepealFutureToBankByFutureManual = emptyMethod;
	//期货发起查询银行余额错误回报
	this.OnErrRtnQueryBankBalanceByFuture = emptyMethod;
	//期货发起冲正银行转期货请求，银行处理完毕后报盘发回的通知
	this.OnRtnRepealFromBankToFutureByFuture = emptyMethod;
	//期货发起冲正期货转银行请求，银行处理完毕后报盘发回的通知
	this.OnRtnRepealFromFutureToBankByFuture = emptyMethod;
	//银行发起银期开户通知
	this.OnRtnOpenAccountByBank = emptyMethod;
	//银行发起银期销户通知
	this.OnRtnCancelAccountByBank = emptyMethod;
	//银行发起变更银行账号通知
	this.OnRtnChangeAccountByBank = emptyMethod;
}

//初始化API
function ssInitApi() {
	//XML请求
	function ssXmlNode(name, requestid) {
		this.requestID = requestid;
		this.name = name;
		this.xml = "<" + name;
	}
	ssXmlNode.prototype.set = function (key, value) {
		if (value) this.xml += " " + key + "=\"" + value + "\" ";
	}
	ssXmlNode.prototype.add = function (child) {
		if (!this.children) this.children = new Array();
		this.children.push(child);
	}
	ssXmlNode.prototype.getdata = function () {
		var s = this.xml;
		if (this.requestID) s += " RequestID=\"" + this.requestID + "\"";
		if (!this.children) return s + "/>";
		s += ">";
		for (var i = 0; i < this.children.length; i++)
			s += this.children[i].getdata();
		return s + "</" + this.name + ">";
	}
	//行情接口
	var M = ssMdApi.prototype;
	M.clientFlag = "SS_MARKET_CLIENT\n\n";
	//通知
	M.Notify = function (msg) {
		var rspInfo = new Object();
		rspInfo.ErrorID = 0;
		rspInfo.ErrorMsg = msg;
		this.OnRspError(rspInfo);
	}
	//初始化
	//@remark 初始化运行环境,只有调用后,接口才开始工作
	M.Init = function () {
		console.log("Init start ");

		var ai = this;
		if (ai.connected) return;
		ai.released = false;
		try {
			var s = ai.socket = new WebSocket(this.frontAddress);
			s.api = ai;
			//连接上时
			s.onopen = function (msg) {
				this.send(ai.clientFlag);
			}
			//接口关闭时
			s.onclose = function (msg) {
				if (ai.connected) {
					ai.OnFrontDisconnected(msg.code);
					ai.connected = false;
				}
				if (!ai.released) {
					if (ai.reconnectcount < 20) ai.reconnectcount++;
					setTimeout(function () { ai.Init(); }, ai.reconnectcount * 1000);
				}
			}
			//接口错误时
			s.onerror = function (msg) { 
				console.log("socket error msg = " + msg);
			}
			//收到服务器消息
			s.onmessage = function (msg) {
				console.log("socket onmessage msg = " + msg.data);
				if (msg.data == "HEART_BEAT\n\n") return;
				if (msg.data == "HEART_NOTIFY\n\n") {
					this.send("HEART_NOTIFY\n\n");
					return;
				}
				if (msg.data == "SS_COUNTER_SERVER\n\n") {

					ai.connected = true;
					ai.reconnectcount = 0;
					ai.requests = new Array();
					ai.maxRequestID = 0;
					ai.OnFrontConnected();
					return;
				}
				var xmldoc = ssGetXmlDoc(msg.data);
				var e = xmldoc.firstChild;
				var func = ai["p_" + e.nodeName];
				if (func) func.call(ai, e);
				else {
					var rspInfo = new Object();
					rspInfo.ErrorID = -2;
					rspInfo.ErrorMsg = "无法识别服务器响应 " + e.nodeName;
					ai.OnRspError(rspInfo);
				}
			}
		}
		catch (ex) {
			var rspInfo = new Object();
			rspInfo.ErrorID = -1;
			rspInfo.ErrorMsg = ex.message;
			if (ai.OnRspError) ai.OnRspError(rspInfo);
			console.log("Init catch exception");
		}
	}

	//关闭接口
	M.Release = function () {
		this.released = true;
		if (this.socket) {
			this.socket.close();
			this.socket = undefined;
		}
	}
	//发送请求
	M.request = function (xr, callback) {
		if (callback) this.requests[xr.requestID] = callback;
		this.socket.send(xr.getdata() + "\n\n");
	}
	//用户登录请求
	M.ReqUserLogin = function (req, callback) {
		var xr = new ssXmlNode("ReqUserLogin", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//用户代码
		xr.set("UserID", req.UserID);
		//密码
		xr.set("Password", req.Password);
		//交易日
		xr.set("TradingDay", req.TradingDay);
		//用户端产品信息
		xr.set("UserProductInfo", req.UserProductInfo);
		//接口端产品信息
		xr.set("InterfaceProductInfo", req.InterfaceProductInfo);
		//协议信息
		xr.set("ProtocolInfo", req.ProtocolInfo);
		//Mac地址
		xr.set("MacAddress", req.MacAddress);
		//动态密码
		xr.set("OneTimePassword", req.OneTimePassword);
		//终端IP地址
		xr.set("ClientIPAddress", req.ClientIPAddress);
		//订阅合约状态通知
		xr.set("SubscribeInstStatus", req.SubscribeInstStatus);
		//版本
		xr.set("Version", "101");
		//发送数据
		this.request(xr, callback);
	}
	//登出请求
	M.ReqUserLogout = function (req, callback) {
		var xr = new ssXmlNode("ReqUserLogout", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//用户代码
		xr.set("UserID", req.UserID);
		//发送数据
		this.request(xr, callback);
	}
	//订阅行情
	//@req 订阅列表的数组
	M.SubscribeMarketData = function (req, callback) {
		var xr = new ssXmlNode("SubscribeMarketData", this.maxRequestID++);
		for (var i = 0; i < req.length; i++) {
			var item = req[i];
			var xitem = new ssXmlNode("item");
			xitem.set("InstrumentID", item.InstrumentID);
			xitem.set("P", item.ProductID);
			xitem.set("E", item.ExchangeID);
			xr.add(xitem);
		}
		this.request(xr, callback);
	}
	//取消订阅行情
	//@req 取消订阅列表的数组
	M.UnSubscribeMarketData = function (req, callback) {
		var xr = new ssXmlNode("UnSubscribeMarketData", this.maxRequestID++);
		for (var i = 0; i < req.length; i++) {
			var item = req[i];
			var xitem = new ssXmlNode("item");
			xitem.set("InstrumentID", item.InstrumentID);
			xitem.set("P", item.ProductID);
			xitem.set("E", item.ExchangeID);
			xr.add(xitem);
		}
		this.request(xr, callback);
	}
	//回调
	M.rspcall = function (data, rspInfo) {
		var callback = this.requests[rspInfo.RequestID];
		if (callback) {
			callback.call(this, data, rspInfo);
			this.requests[rspInfo.RequestID] = undefined;
		}
	}
	//登录请求响应
	M.p_OnRspUserLogin = function (e) {
		var rspInfo = getRspInfo(e);
		var data = new Object();
		//交易日
		data.TradingDay = getString(e, "TradingDay");
		//登录成功时间
		data.LoginTime = getString(e, "LoginTime");
		//经纪公司代码
		data.BrokerID = getString(e, "BrokerID");
		//用户代码
		data.UserID = getString(e, "UserID");
		//交易系统名称
		data.SystemName = getString(e, "SystemName");
		//前置编号
		data.FrontID = getInt(e, "FrontID");
		//会话编号
		data.SessionID = getInt(e, "SessionID");
		//最大报单引用
		data.MaxOrderRef = getString(e, "MaxOrderRef");
		//上期所时间
		data.SHFETime = getString(e, "SHFETime");
		//大商所时间
		data.DCETime = getString(e, "DCETime");
		//郑商所时间
		data.CZCETime = getString(e, "CZCETime");
		//中金所时间
		data.FFEXTime = getString(e, "FFEXTime");
		//基准币种代码
		data.BaseCurrencyID = getString(e, "BaseCurrencyID");
		//结算时间
		data.SettlementTime = getString(e, "SettlementTime");
		//回调
		this.rspcall(data, rspInfo);
	}
	//登出请求响应
	M.p_OnRspUserLogout = function (e) {
		var rspInfo = getRspInfo(e);
		var data = new Object();
		//经纪公司代码
		data.BrokerID = getString(e, "BrokerID");
		//用户代码
		data.UserID = getString(e, "UserID");
		//回调
		this.rspcall(data, rspInfo);
	}
	//订阅行情应答
	M.p_OnRspSubMarketData = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (var i = e.firstChild; i; i = i.nextElementSibling) {
			var item = new Object();
			datas.push(item);
			item.InstrumentID = getString(e, "InstrumentID");
			item.ExchangeID = getString(e, "ExchangeID");
		}
		//回调
		this.rspcall(datas, rspInfo);
	}
	//取消订阅行情应答
	M.p_OnRspUnSubMarketData = M.p_OnRspSubMarketData;
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


	//交易接口 --------------------------------
	var T = ssTraderApi.prototype;
	T.clientFlag = "SS_TRADER_CLIENT\n\n";
	T.Init = M.Init;

	//关闭接口
	T.Release = M.Release;
	//发送请求
	T.request = M.request;

	//请求查询行情
	T.ReqQryDepthMarketData = function (req, callback) {
		var xr = new ssXmlNode("ReqQryDepthMarketData", this.maxRequestID++);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//发送数据
		this.request(xr, callback);
	}

	//合约交易状态通知
	M.p_OnRtnInstrumentStatus = function (e) {
		//循环遍历
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			//交易所代码
			data.ExchangeID = getString(e, "E");
			//合约在交易所的代码
			data.ExchangeInstID = getString(e, "EI");
			//结算组代码
			data.SettlementGroupID = getString(e, "SG");
			//合约代码
			data.InstrumentID = getString(e, "I");
			//合约交易状态
			data.InstrumentStatus = getString(e, "IS");
			//交易阶段编号
			data.TradingSegmentSN = getString(e, "TS");
			//进入本状态时间
			data.EnterTime = getString(e, "T");
			//进入本状态原因
			data.EnterReason = getString(e, "R");
			//回调
			this.OnRtnInstrumentStatus(data);
		}
	}
	
	//合约交易状态通知
	T.p_OnRtnInstrumentStatus = M.p_OnRtnInstrumentStatus;
	
	//获取整数
	function getInt(e, name) {
		var v = e.getAttribute(name);
		if (v) return parseInt(v);
		else return 0;
	}

	//获取浮点
	function getDouble(e, name) {
		var v = e.getAttribute(name);
		if (v) return parseFloat(v);
		else return 0;
	}
	//获取浮点(默认为空值)
	function getDoubleN(e, name) {
		var v = e.getAttribute(name);
		if (v) return parseFloat(v);
		else return Number.NaN;
	}

	//获取字符串
	function getString(e, name) {
		var v = e.getAttribute(name);
		if (v) return v;
		else return "";
	}

	//获取日期
	function getDate(e, name) {
		var v = e.getAttribute(name);
		if (!v || v.length != 8) return Number.NaN;
		return new Date(v.substr(0, 4), v.substr(4, 2), v.substr(6, 2));
	}

	//获取时间和日期
	function getDateTime(e, name) {
		var v = e.getAttribute(name);
		if (!v) return null;
		return new Date(v);
	}

	//解析响应信息
	function getRspInfo(e) {
		var rspInfo = new Object();
		rspInfo.RequestID = getInt(e, "RequestID");
		rspInfo.ErrorID = getInt(e, "ErrorID");
		rspInfo.ErrorMsg = getString(e, "ErrorMsg");
		return rspInfo;
	}

	//处理空响应
	function nullRsp(e) {
		var rspInfo = getRspInfo(e);
		var data = new Object();
		this.rspcall(data, rspInfo);
	}

}
ssInitApi();
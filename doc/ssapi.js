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

	//交易接口 --------------------------------
	var T = ssTraderApi.prototype;
	T.clientFlag = "SS_TRADER_CLIENT\n\n";
	T.Init = M.Init;

	//关闭接口
	T.Release = M.Release;
	//发送请求
	T.request = M.request;

	//订阅私有流。
	//@param nResumeType 私有流重传方式  
	//        0:从本交易日开始重传
	//        1:从上次收到的续传
	//        2:只传送登录后私有流的内容
	//@remark 该方法要在Init方法前调用。若不调用则不会收到私有流的数据。
	T.SubscribePrivateTopic = function (ResumeType) { this.privateTopic = ResumeType; }

	//订阅公共流。
	//@param nResumeType 公共流重传方式  
	//        0:从本交易日开始重传
	//        1:从上次收到的续传
	//        2:只传送登录后公共流的内容
	//@remark 该方法要在Init方法前调用。若不调用则不会收到公共流的数据。
	T.SubscribePublicTopic = function (ResumeType) { this.publicTopic = ResumeType; }

	//客户端认证请求
	T.ReqAuthenticate = function (req, callback) {
		var xr = new ssXmlNode("ReqAuthenticate", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//用户代码
		xr.set("UserID", req.UserID);
		//用户端产品信息
		xr.set("UserProductInfo", req.UserProductInfo);
		//认证码
		xr.set("AuthCode", req.AuthCode);
		//发送数据
		this.request(xr, callback);
	}

	//用户登录请求
	T.ReqUserLogin = function (req, callback) {
		var xr = new ssXmlNode("ReqUserLogin", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//用户代码
		xr.set("UserID", req.UserID);
		//密码
		xr.set("Password", req.Password);
		//公有流
		switch (this.publicTopic) {
			case 2: xr.set("PublicTopic", "Q"); break;
			case 1: xr.set("PublicTopic", this.publicTopicIndex); break;
			default: xr.set("PublicTopic", "0"); break;
		}
		//私有流
		switch (this.privateTopic) {
			case 2: xr.set("PrivateTopic", "Q"); break;
			case 1: xr.set("PrivateTopic", this.privateTopicIndex); break;
			default: xr.set("PrivateTopic", "0"); break;
		}
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
		//发送数据
		this.request(xr, callback);
	}

	//登出请求
	T.ReqUserLogout = M.ReqUserLogout;

	//用户口令更新请求
	T.ReqUserPasswordUpdate = function (req, callback) {
		var xr = new ssXmlNode("ReqUserPasswordUpdate", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//用户代码
		xr.set("UserID", req.UserID);
		//原来的口令
		xr.set("OldPassword", req.OldPassword);
		//新的口令
		xr.set("NewPassword", req.NewPassword);
		//发送数据
		this.request(xr, callback);
	}

	//资金账户口令更新请求
	T.ReqTradingAccountPasswordUpdate = function (req, callback) {
		var xr = new ssXmlNode("ReqTradingAccountPasswordUpdate", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者帐号
		xr.set("AccountID", req.AccountID);
		//原来的口令
		xr.set("OldPassword", req.OldPassword);
		//新的口令
		xr.set("NewPassword", req.NewPassword);
		//币种代码
		xr.set("CurrencyID", req.CurrencyID);
		//发送数据
		this.request(xr, callback);
	}

	//报单录入请求
	T.ReqOrderInsert = function (req, callback) {
		var xr = new ssXmlNode("ReqOrderInsert", this.maxRequestID++);
		//经纪公司代码
		xr.set("B", req.BrokerID);
		//投资者代码
		xr.set("A", req.InvestorID);
		//交易所代码
		xr.set("E", req.ExchangeID);
		//合约代码
		xr.set("I", req.InstrumentID);
		//报单引用
		xr.set("OR", req.OrderRef);
		//用户代码
		xr.set("U", req.UserID);
		//报单价格条件
		xr.set("OPT", req.OrderPriceType);
		//买卖方向
		xr.set("DF", req.Direction);
		//组合开平标志
		xr.set("OF", req.CombOffsetFlag0);
		//组合投机套保标志
		xr.set("HF", req.CombHedgeFlag0);
		//借款比例
		xr.set("LO", req.LoanRatio);
		//价格
		xr.set("LP", req.LimitPrice);
		//数量
		xr.set("V", req.VolumeTotalOriginal);
		//有效期类型
		xr.set("TC", req.TimeCondition);
		//GTD日期
		xr.set("GD", req.GTDDate);
		//成交量类型
		xr.set("VC", req.VolumeCondition);
		//最小成交量
		xr.set("MV", req.MinVolume);
		//触发条件
		xr.set("CC", req.ContingentCondition);
		//止损价
		xr.set("SP", req.StopPrice);
		//止盈价
		xr.set("TP", req.TargetPrice);
		//自动止盈止损类型
		xr.set("A1", req.AutoStopType);
		//强平原因
		xr.set("FCR", req.ForceCloseReason);
		//业务单元
		xr.set("BU", req.BusinessUnit);
		//用户强评标志
		xr.set("UF", req.UserForceClose);
		//互换单标志
		xr.set("SO", req.IsSwapOrder);
		//发送数据
		this.request(xr, callback);
	}

	//持仓自动化操作
	T.ReqPositionAutomation = function (req, callback) {
		var xr = new ssXmlNode("ReqPositionAutomation", this.maxRequestID++);
		//操作编号
		xr.set("N", req.ActionID);
		//交易所代码
		xr.set("E", req.ExchangeID);
		//合约代码
		xr.set("I", req.InstrumentID);
		//投机套保标志
		xr.set("H", req.HedgeFlag);
		//买卖方向
		xr.set("D", req.Direction);
		//止盈价格
		xr.set("TP", req.TargetPrice);
		//止盈数量
		xr.set("TV", req.TargetVolume);
		//止盈浮动跳数
		xr.set("T1", req.TargetPlus);
		//止损价格
		xr.set("SP", req.StopPrice);
		//止损数量
		xr.set("SV", req.StopVolume);
		//止损浮动跳数
		xr.set("S1", req.StopPlus);
		//业务单元
		xr.set("BU", req.BusinessUnit);
		//发送数据
		this.request(xr, callback);
	}

	//预埋单录入请求
	T.ReqParkedOrderInsert = function (req, callback) {
		var xr = new ssXmlNode("ReqParkedOrderInsert", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//报单引用
		xr.set("OrderRef", req.OrderRef);
		//用户代码
		xr.set("UserID", req.UserID);
		//报单价格条件
		xr.set("OrderPriceType", req.OrderPriceType);
		//买卖方向
		xr.set("Direction", req.Direction);
		//组合开平标志
		xr.set("CombOffsetFlag", req.CombOffsetFlag);
		//组合投机套保标志
		xr.set("CombHedgeFlag", req.CombHedgeFlag);
		//价格
		xr.set("LimitPrice", req.LimitPrice);
		//数量
		xr.set("VolumeTotalOriginal", req.VolumeTotalOriginal);
		//有效期类型
		xr.set("TimeCondition", req.TimeCondition);
		//GTD日期
		xr.set("GTDDate", req.GTDDate);
		//成交量类型
		xr.set("VolumeCondition", req.VolumeCondition);
		//最小成交量
		xr.set("MinVolume", req.MinVolume);
		//触发条件
		xr.set("ContingentCondition", req.ContingentCondition);
		//止损价
		xr.set("StopPrice", req.StopPrice);
		//强平原因
		xr.set("ForceCloseReason", req.ForceCloseReason);
		//自动挂起标志
		xr.set("IsAutoSuspend", req.IsAutoSuspend);
		//业务单元
		xr.set("BusinessUnit", req.BusinessUnit);
		//请求编号
		xr.set("RequestID", req.RequestID);
		//用户强评标志
		xr.set("UserForceClose", req.UserForceClose);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//预埋报单编号
		xr.set("ParkedOrderID", req.ParkedOrderID);
		//用户类型
		xr.set("UserType", req.UserType);
		//预埋单状态
		xr.set("Status", req.Status);
		//错误代码
		xr.set("ErrorID", req.ErrorID);
		//错误信息
		xr.set("ErrorMsg", req.ErrorMsg);
		//互换单标志
		xr.set("IsSwapOrder", req.IsSwapOrder);
		//发送数据
		this.request(xr, callback);
	}

	//预埋撤单录入请求
	T.ReqParkedOrderAction = function (req, callback) {
		var xr = new ssXmlNode("ReqParkedOrderAction", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//报单操作引用
		xr.set("OrderActionRef", req.OrderActionRef);
		//报单引用
		xr.set("OrderRef", req.OrderRef);
		//请求编号
		xr.set("RequestID", req.RequestID);
		//前置编号
		xr.set("FrontID", req.FrontID);
		//会话编号
		xr.set("SessionID", req.SessionID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//报单编号
		xr.set("OrderSysID", req.OrderSysID);
		//操作标志
		xr.set("ActionFlag", req.ActionFlag);
		//价格
		xr.set("LimitPrice", req.LimitPrice);
		//数量变化
		xr.set("VolumeChange", req.VolumeChange);
		//用户代码
		xr.set("UserID", req.UserID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//预埋撤单单编号
		xr.set("ParkedOrderActionID", req.ParkedOrderActionID);
		//用户类型
		xr.set("UserType", req.UserType);
		//预埋撤单状态
		xr.set("Status", req.Status);
		//错误代码
		xr.set("ErrorID", req.ErrorID);
		//错误信息
		xr.set("ErrorMsg", req.ErrorMsg);
		//发送数据
		this.request(xr, callback);
	}

	//报单操作请求
	T.ReqOrderAction = function (req, callback) {
		var xr = new ssXmlNode("ReqOrderAction", this.maxRequestID++);
		//经纪公司代码
		xr.set("B", req.BrokerID);
		//投资者代码
		xr.set("A", req.InvestorID);
		//报单操作引用
		xr.set("AR", req.OrderActionRef);
		//报单引用
		xr.set("OR", req.OrderRef);
		//前置编号
		xr.set("F", req.FrontID);
		//会话编号
		xr.set("SS", req.SessionID);
		//交易所代码
		xr.set("E", req.ExchangeID);
		//报单编号
		xr.set("OSI", req.OrderSysID);
		//操作标志
		xr.set("AF", req.ActionFlag);
		//价格
		xr.set("LP", req.LimitPrice);
		//数量变化
		xr.set("V", req.VolumeChange);
		//用户代码
		xr.set("U", req.UserID);
		//合约代码
		xr.set("I", req.InstrumentID);
		//发送数据
		this.request(xr, callback);
	}

	//查询最大报单数量请求
	T.ReqQueryMaxOrderVolume = function (req, callback) {
		var xr = new ssXmlNode("ReqQueryMaxOrderVolume", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//买卖方向
		xr.set("Direction", req.Direction);
		//开平标志
		xr.set("OffsetFlag", req.OffsetFlag);
		//投机套保标志
		xr.set("HedgeFlag", req.HedgeFlag);
		//最大允许报单数量
		xr.set("MaxVolume", req.MaxVolume);
		//发送数据
		this.request(xr, callback);
	}

	//投资者结算结果确认
	T.ReqSettlementInfoConfirm = function (req, callback) {
		var xr = new ssXmlNode("ReqSettlementInfoConfirm", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//确认日期
		xr.set("ConfirmDate", req.ConfirmDate);
		//确认时间
		xr.set("ConfirmTime", req.ConfirmTime);
		//发送数据
		this.request(xr, callback);
	}

	//请求删除预埋单
	T.ReqRemoveParkedOrder = function (req, callback) {
		var xr = new ssXmlNode("ReqRemoveParkedOrder", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//预埋报单编号
		xr.set("ParkedOrderID", req.ParkedOrderID);
		//发送数据
		this.request(xr, callback);
	}

	//请求删除预埋撤单
	T.ReqRemoveParkedOrderAction = function (req, callback) {
		var xr = new ssXmlNode("ReqRemoveParkedOrderAction", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//预埋撤单编号
		xr.set("ParkedOrderActionID", req.ParkedOrderActionID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询报单
	T.ReqQryOrder = function (req, callback) {
		var xr = new ssXmlNode("ReqQryOrder", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//报单编号
		xr.set("OrderSysID", req.OrderSysID);
		//开始时间
		xr.set("InsertTimeStart", req.InsertTimeStart);
		//结束时间
		xr.set("InsertTimeEnd", req.InsertTimeEnd);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询成交
	T.ReqQryTrade = function (req, callback) {
		var xr = new ssXmlNode("ReqQryTrade", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//成交编号
		xr.set("TradeID", req.TradeID);
		//开始时间
		xr.set("TradeTimeStart", req.TradeTimeStart);
		//结束时间
		xr.set("TradeTimeEnd", req.TradeTimeEnd);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询投资者持仓
	T.ReqQryInvestorPosition = function (req, callback) {
		var xr = new ssXmlNode("ReqQryInvestorPosition", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询资金账户
	T.ReqQryTradingAccount = function (req, callback) {
		var xr = new ssXmlNode("ReqQryTradingAccount", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//币种代码
		xr.set("CurrencyID", req.CurrencyID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询投资者
	T.ReqQryInvestor = function (req, callback) {
		var xr = new ssXmlNode("ReqQryInvestor", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询交易编码
	T.ReqQryTradingCode = function (req, callback) {
		var xr = new ssXmlNode("ReqQryTradingCode", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//客户代码
		xr.set("ClientID", req.ClientID);
		//交易编码类型
		xr.set("ClientIDType", req.ClientIDType);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询合约保证金率
	T.ReqQryInstrumentMarginRate = function (req, callback) {
		var xr = new ssXmlNode("ReqQryInstrumentMarginRate", this.maxRequestID++);
		//经纪公司代码
		xr.set("B", req.BrokerID);
		//投资者代码
		xr.set("IN", req.InvestorID);
		//合约代码
		xr.set("I", req.InstrumentID);
		//投机套保标志
		xr.set("H", req.HedgeFlag);
		//交易所代码
		xr.set("E", req.ExchangeID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询合约手续费率
	T.ReqQryInstrumentCommissionRate = function (req, callback) {
		var xr = new ssXmlNode("ReqQryInstrumentCommissionRate", this.maxRequestID++);
		//经纪公司代码
		xr.set("B", req.BrokerID);
		//投资者代码
		xr.set("IN", req.InvestorID);
		//合约代码
		xr.set("I", req.InstrumentID);
		//交易所代码
		xr.set("E", req.ExchangeID);
		//投机套保标志
		xr.set("H", req.HedgeFlag);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询交易所
	T.ReqQryExchange = function (req, callback) {
		var xr = new ssXmlNode("ReqQryExchange", this.maxRequestID++);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询合约
	T.ReqQryInstrument = function (req, callback) {
		var xr = new ssXmlNode("ReqQryInstrument", this.maxRequestID++);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//合约在交易所的代码
		xr.set("ExchangeInstID", req.ExchangeInstID);
		//产品代码
		xr.set("ProductID", req.ProductID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询行情
	T.ReqQryDepthMarketData = function (req, callback) {
		var xr = new ssXmlNode("ReqQryDepthMarketData", this.maxRequestID++);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询投资者结算结果
	T.ReqQrySettlementInfo = function (req, callback) {
		var xr = new ssXmlNode("ReqQrySettlementInfo", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//交易日
		xr.set("TradingDay", req.TradingDay);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询转帐银行
	T.ReqQryTransferBank = function (req, callback) {
		var xr = new ssXmlNode("ReqQryTransferBank", this.maxRequestID++);
		//银行代码
		xr.set("BankID", req.BankID);
		//银行分中心代码
		xr.set("BankBrchID", req.BankBrchID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询投资者持仓明细
	T.ReqQryInvestorPositionDetail = function (req, callback) {
		var xr = new ssXmlNode("ReqQryInvestorPositionDetail", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询客户通知
	T.ReqQryNotice = function (req, callback) {
		var xr = new ssXmlNode("ReqQryNotice", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询结算信息确认
	T.ReqQrySettlementInfoConfirm = function (req, callback) {
		var xr = new ssXmlNode("ReqQrySettlementInfoConfirm", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询投资者持仓明细
	T.ReqQryInvestorPositionCombineDetail = function (req, callback) {
		var xr = new ssXmlNode("ReqQryInvestorPositionCombineDetail", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//组合持仓合约编码
		xr.set("CombInstrumentID", req.CombInstrumentID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询保证金监管系统经纪公司资金账户密钥
	T.ReqQryCFMMCTradingAccountKey = function (req, callback) {
		var xr = new ssXmlNode("ReqQryCFMMCTradingAccountKey", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询仓单折抵信息
	T.ReqQryEWarrantOffset = function (req, callback) {
		var xr = new ssXmlNode("ReqQryEWarrantOffset", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询转帐流水
	T.ReqQryTransferSerial = function (req, callback) {
		var xr = new ssXmlNode("ReqQryTransferSerial", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者帐号
		xr.set("AccountID", req.AccountID);
		//银行编码
		xr.set("BankID", req.BankID);
		//币种代码
		xr.set("CurrencyID", req.CurrencyID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询银期签约关系
	T.ReqQryAccountregister = function (req, callback) {
		var xr = new ssXmlNode("ReqQryAccountregister", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者帐号
		xr.set("AccountID", req.AccountID);
		//银行编码
		xr.set("BankID", req.BankID);
		//银行分支机构编码
		xr.set("BankBranchID", req.BankBranchID);
		//币种代码
		xr.set("CurrencyID", req.CurrencyID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询签约银行
	T.ReqQryContractBank = function (req, callback) {
		var xr = new ssXmlNode("ReqQryContractBank", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//银行代码
		xr.set("BankID", req.BankID);
		//银行分中心代码
		xr.set("BankBrchID", req.BankBrchID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询预埋单
	T.ReqQryParkedOrder = function (req, callback) {
		var xr = new ssXmlNode("ReqQryParkedOrder", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询预埋撤单
	T.ReqQryParkedOrderAction = function (req, callback) {
		var xr = new ssXmlNode("ReqQryParkedOrderAction", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询交易通知
	T.ReqQryTradingNotice = function (req, callback) {
		var xr = new ssXmlNode("ReqQryTradingNotice", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询经纪公司交易参数
	T.ReqQryBrokerTradingParams = function (req, callback) {
		var xr = new ssXmlNode("ReqQryBrokerTradingParams", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//币种代码
		xr.set("CurrencyID", req.CurrencyID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询经纪公司交易算法
	T.ReqQryBrokerTradingAlgos = function (req, callback) {
		var xr = new ssXmlNode("ReqQryBrokerTradingAlgos", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//发送数据
		this.request(xr, callback);
	}

	//期货发起银行资金转期货请求
	T.ReqFromBankToFutureByFuture = function (req, callback) {
		var xr = new ssXmlNode("ReqFromBankToFutureByFuture", this.maxRequestID++);
		//业务功能码
		xr.set("TradeCode", req.TradeCode);
		//银行代码
		xr.set("BankID", req.BankID);
		//银行分支机构代码
		xr.set("BankBranchID", req.BankBranchID);
		//期商代码
		xr.set("BrokerID", req.BrokerID);
		//期商分支机构代码
		xr.set("BrokerBranchID", req.BrokerBranchID);
		//交易日期
		xr.set("TradeDate", req.TradeDate);
		//交易时间
		xr.set("TradeTime", req.TradeTime);
		//银行流水号
		xr.set("BankSerial", req.BankSerial);
		//交易系统日期 
		xr.set("TradingDay", req.TradingDay);
		//银期平台消息流水号
		xr.set("PlateSerial", req.PlateSerial);
		//最后分片标志
		xr.set("LastFragment", req.LastFragment);
		//会话号
		xr.set("SessionID", req.SessionID);
		//客户姓名
		xr.set("CustomerName", req.CustomerName);
		//证件类型
		xr.set("IdCardType", req.IdCardType);
		//证件号码
		xr.set("IdentifiedCardNo", req.IdentifiedCardNo);
		//客户类型
		xr.set("CustType", req.CustType);
		//银行帐号
		xr.set("BankAccount", req.BankAccount);
		//银行密码
		xr.set("BankPassWord", req.BankPassWord);
		//投资者帐号
		xr.set("AccountID", req.AccountID);
		//期货密码
		xr.set("Password", req.Password);
		//安装编号
		xr.set("InstallID", req.InstallID);
		//期货公司流水号
		xr.set("FutureSerial", req.FutureSerial);
		//用户标识
		xr.set("UserID", req.UserID);
		//验证客户证件号码标志
		xr.set("VerifyCertNoFlag", req.VerifyCertNoFlag);
		//币种代码
		xr.set("CurrencyID", req.CurrencyID);
		//转帐金额
		xr.set("TradeAmount", req.TradeAmount);
		//期货可取金额
		xr.set("FutureFetchAmount", req.FutureFetchAmount);
		//费用支付标志
		xr.set("FeePayFlag", req.FeePayFlag);
		//应收客户费用
		xr.set("CustFee", req.CustFee);
		//应收期货公司费用
		xr.set("BrokerFee", req.BrokerFee);
		//发送方给接收方的消息
		xr.set("Message", req.Message);
		//摘要
		xr.set("Digest", req.Digest);
		//银行帐号类型
		xr.set("BankAccType", req.BankAccType);
		//渠道标志
		xr.set("DeviceID", req.DeviceID);
		//期货单位帐号类型
		xr.set("BankSecuAccType", req.BankSecuAccType);
		//期货公司银行编码
		xr.set("BrokerIDByBank", req.BrokerIDByBank);
		//期货单位帐号
		xr.set("BankSecuAcc", req.BankSecuAcc);
		//银行密码标志
		xr.set("BankPwdFlag", req.BankPwdFlag);
		//期货资金密码核对标志
		xr.set("SecuPwdFlag", req.SecuPwdFlag);
		//交易柜员
		xr.set("OperNo", req.OperNo);
		//请求编号
		xr.set("RequestID", req.RequestID);
		//交易ID
		xr.set("TID", req.TID);
		//转账交易状态
		xr.set("TransferStatus", req.TransferStatus);
		//发送数据
		this.request(xr, callback);
	}

	//期货发起期货资金转银行请求
	T.ReqFromFutureToBankByFuture = function (req, callback) {
		var xr = new ssXmlNode("ReqFromFutureToBankByFuture", this.maxRequestID++);
		//业务功能码
		xr.set("TradeCode", req.TradeCode);
		//银行代码
		xr.set("BankID", req.BankID);
		//银行分支机构代码
		xr.set("BankBranchID", req.BankBranchID);
		//期商代码
		xr.set("BrokerID", req.BrokerID);
		//期商分支机构代码
		xr.set("BrokerBranchID", req.BrokerBranchID);
		//交易日期
		xr.set("TradeDate", req.TradeDate);
		//交易时间
		xr.set("TradeTime", req.TradeTime);
		//银行流水号
		xr.set("BankSerial", req.BankSerial);
		//交易系统日期 
		xr.set("TradingDay", req.TradingDay);
		//银期平台消息流水号
		xr.set("PlateSerial", req.PlateSerial);
		//最后分片标志
		xr.set("LastFragment", req.LastFragment);
		//会话号
		xr.set("SessionID", req.SessionID);
		//客户姓名
		xr.set("CustomerName", req.CustomerName);
		//证件类型
		xr.set("IdCardType", req.IdCardType);
		//证件号码
		xr.set("IdentifiedCardNo", req.IdentifiedCardNo);
		//客户类型
		xr.set("CustType", req.CustType);
		//银行帐号
		xr.set("BankAccount", req.BankAccount);
		//银行密码
		xr.set("BankPassWord", req.BankPassWord);
		//投资者帐号
		xr.set("AccountID", req.AccountID);
		//期货密码
		xr.set("Password", req.Password);
		//安装编号
		xr.set("InstallID", req.InstallID);
		//期货公司流水号
		xr.set("FutureSerial", req.FutureSerial);
		//用户标识
		xr.set("UserID", req.UserID);
		//验证客户证件号码标志
		xr.set("VerifyCertNoFlag", req.VerifyCertNoFlag);
		//币种代码
		xr.set("CurrencyID", req.CurrencyID);
		//转帐金额
		xr.set("TradeAmount", req.TradeAmount);
		//期货可取金额
		xr.set("FutureFetchAmount", req.FutureFetchAmount);
		//费用支付标志
		xr.set("FeePayFlag", req.FeePayFlag);
		//应收客户费用
		xr.set("CustFee", req.CustFee);
		//应收期货公司费用
		xr.set("BrokerFee", req.BrokerFee);
		//发送方给接收方的消息
		xr.set("Message", req.Message);
		//摘要
		xr.set("Digest", req.Digest);
		//银行帐号类型
		xr.set("BankAccType", req.BankAccType);
		//渠道标志
		xr.set("DeviceID", req.DeviceID);
		//期货单位帐号类型
		xr.set("BankSecuAccType", req.BankSecuAccType);
		//期货公司银行编码
		xr.set("BrokerIDByBank", req.BrokerIDByBank);
		//期货单位帐号
		xr.set("BankSecuAcc", req.BankSecuAcc);
		//银行密码标志
		xr.set("BankPwdFlag", req.BankPwdFlag);
		//期货资金密码核对标志
		xr.set("SecuPwdFlag", req.SecuPwdFlag);
		//交易柜员
		xr.set("OperNo", req.OperNo);
		//请求编号
		xr.set("RequestID", req.RequestID);
		//交易ID
		xr.set("TID", req.TID);
		//转账交易状态
		xr.set("TransferStatus", req.TransferStatus);
		//发送数据
		this.request(xr, callback);
	}

	//期货发起查询银行余额请求
	T.ReqQueryBankAccountMoneyByFuture = function (req, callback) {
		var xr = new ssXmlNode("ReqQueryBankAccountMoneyByFuture", this.maxRequestID++);
		//业务功能码
		xr.set("TradeCode", req.TradeCode);
		//银行代码
		xr.set("BankID", req.BankID);
		//银行分支机构代码
		xr.set("BankBranchID", req.BankBranchID);
		//期商代码
		xr.set("BrokerID", req.BrokerID);
		//期商分支机构代码
		xr.set("BrokerBranchID", req.BrokerBranchID);
		//交易日期
		xr.set("TradeDate", req.TradeDate);
		//交易时间
		xr.set("TradeTime", req.TradeTime);
		//银行流水号
		xr.set("BankSerial", req.BankSerial);
		//交易系统日期 
		xr.set("TradingDay", req.TradingDay);
		//银期平台消息流水号
		xr.set("PlateSerial", req.PlateSerial);
		//最后分片标志
		xr.set("LastFragment", req.LastFragment);
		//会话号
		xr.set("SessionID", req.SessionID);
		//客户姓名
		xr.set("CustomerName", req.CustomerName);
		//证件类型
		xr.set("IdCardType", req.IdCardType);
		//证件号码
		xr.set("IdentifiedCardNo", req.IdentifiedCardNo);
		//客户类型
		xr.set("CustType", req.CustType);
		//银行帐号
		xr.set("BankAccount", req.BankAccount);
		//银行密码
		xr.set("BankPassWord", req.BankPassWord);
		//投资者帐号
		xr.set("AccountID", req.AccountID);
		//期货密码
		xr.set("Password", req.Password);
		//期货公司流水号
		xr.set("FutureSerial", req.FutureSerial);
		//安装编号
		xr.set("InstallID", req.InstallID);
		//用户标识
		xr.set("UserID", req.UserID);
		//验证客户证件号码标志
		xr.set("VerifyCertNoFlag", req.VerifyCertNoFlag);
		//币种代码
		xr.set("CurrencyID", req.CurrencyID);
		//摘要
		xr.set("Digest", req.Digest);
		//银行帐号类型
		xr.set("BankAccType", req.BankAccType);
		//渠道标志
		xr.set("DeviceID", req.DeviceID);
		//期货单位帐号类型
		xr.set("BankSecuAccType", req.BankSecuAccType);
		//期货公司银行编码
		xr.set("BrokerIDByBank", req.BrokerIDByBank);
		//期货单位帐号
		xr.set("BankSecuAcc", req.BankSecuAcc);
		//银行密码标志
		xr.set("BankPwdFlag", req.BankPwdFlag);
		//期货资金密码核对标志
		xr.set("SecuPwdFlag", req.SecuPwdFlag);
		//交易柜员
		xr.set("OperNo", req.OperNo);
		//请求编号
		xr.set("RequestID", req.RequestID);
		//交易ID
		xr.set("TID", req.TID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询产品
	T.ReqQryProduct = function (req, callback) {
		var xr = new ssXmlNode("ReqQryProduct", this.maxRequestID++);
		//产品代码
		xr.set("ProductID", req.ProductID);
		//产品类型
		xr.set("ProductClass", req.ProductClass);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询投资者品种/跨品种保证金
	T.ReqQryInvestorProductGroupMargin = function (req, callback) {
		var xr = new ssXmlNode("ReqQryInvestorProductGroupMargin", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//品种/跨品种标示
		xr.set("ProductGroupID", req.ProductGroupID);
		//投机套保标志
		xr.set("HedgeFlag", req.HedgeFlag);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询交易所保证金率
	T.ReqQryExchangeMarginRate = function (req, callback) {
		var xr = new ssXmlNode("ReqQryExchangeMarginRate", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//投机套保标志
		xr.set("HedgeFlag", req.HedgeFlag);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询交易所调整保证金率
	T.ReqQryExchangeMarginRateAdjust = function (req, callback) {
		var xr = new ssXmlNode("ReqQryExchangeMarginRateAdjust", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//投机套保标志
		xr.set("HedgeFlag", req.HedgeFlag);
		//发送数据
		this.request(xr, callback);
	}

	//执行宣告录入请求
	T.ReqExecOrderInsert = function (req, callback) {
		var xr = new ssXmlNode("ReqExecOrderInsert", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//执行宣告引用
		xr.set("ExecOrderRef", req.ExecOrderRef);
		//用户代码
		xr.set("UserID", req.UserID);
		//数量
		xr.set("Volume", req.Volume);
		//请求编号
		xr.set("RequestID", req.RequestID);
		//业务单元
		xr.set("BusinessUnit", req.BusinessUnit);
		//开平标志
		xr.set("OffsetFlag", req.OffsetFlag);
		//投机套保标志
		xr.set("HedgeFlag", req.HedgeFlag);
		//执行类型
		xr.set("ActionType", req.ActionType);
		//保留头寸申请的持仓方向
		xr.set("PosiDirection", req.PosiDirection);
		//期权行权后是否保留期货头寸的标记
		xr.set("ReservePositionFlag", req.ReservePositionFlag);
		//期权行权后生成的头寸是否自动平仓
		xr.set("CloseFlag", req.CloseFlag);
		//发送数据
		this.request(xr, callback);
	}

	//执行宣告操作请求
	T.ReqExecOrderAction = function (req, callback) {
		var xr = new ssXmlNode("ReqExecOrderAction", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//执行宣告操作引用
		xr.set("ExecOrderActionRef", req.ExecOrderActionRef);
		//执行宣告引用
		xr.set("ExecOrderRef", req.ExecOrderRef);
		//请求编号
		xr.set("RequestID", req.RequestID);
		//前置编号
		xr.set("FrontID", req.FrontID);
		//会话编号
		xr.set("SessionID", req.SessionID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//执行宣告操作编号
		xr.set("ExecOrderSysID", req.ExecOrderSysID);
		//操作标志
		xr.set("ActionFlag", req.ActionFlag);
		//用户代码
		xr.set("UserID", req.UserID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//发送数据
		this.request(xr, callback);
	}

	//询价录入请求
	T.ReqForQuoteInsert = function (req, callback) {
		var xr = new ssXmlNode("ReqForQuoteInsert", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//询价引用
		xr.set("ForQuoteRef", req.ForQuoteRef);
		//用户代码
		xr.set("UserID", req.UserID);
		//发送数据
		this.request(xr, callback);
	}

	//报价录入请求
	T.ReqQuoteInsert = function (req, callback) {
		var xr = new ssXmlNode("ReqQuoteInsert", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//报价引用
		xr.set("QuoteRef", req.QuoteRef);
		//用户代码
		xr.set("UserID", req.UserID);
		//卖价格
		xr.set("AskPrice", req.AskPrice);
		//买价格
		xr.set("BidPrice", req.BidPrice);
		//卖数量
		xr.set("AskVolume", req.AskVolume);
		//买数量
		xr.set("BidVolume", req.BidVolume);
		//请求编号
		xr.set("RequestID", req.RequestID);
		//业务单元
		xr.set("BusinessUnit", req.BusinessUnit);
		//卖开平标志
		xr.set("AskOffsetFlag", req.AskOffsetFlag);
		//买开平标志
		xr.set("BidOffsetFlag", req.BidOffsetFlag);
		//卖投机套保标志
		xr.set("AskHedgeFlag", req.AskHedgeFlag);
		//买投机套保标志
		xr.set("BidHedgeFlag", req.BidHedgeFlag);
		//衍生卖报单引用
		xr.set("AskOrderRef", req.AskOrderRef);
		//衍生买报单引用
		xr.set("BidOrderRef", req.BidOrderRef);
		//发送数据
		this.request(xr, callback);
	}

	//报价操作请求
	T.ReqQuoteAction = function (req, callback) {
		var xr = new ssXmlNode("ReqQuoteAction", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//报价操作引用
		xr.set("QuoteActionRef", req.QuoteActionRef);
		//报价引用
		xr.set("QuoteRef", req.QuoteRef);
		//请求编号
		xr.set("RequestID", req.RequestID);
		//前置编号
		xr.set("FrontID", req.FrontID);
		//会话编号
		xr.set("SessionID", req.SessionID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//报价操作编号
		xr.set("QuoteSysID", req.QuoteSysID);
		//操作标志
		xr.set("ActionFlag", req.ActionFlag);
		//用户代码
		xr.set("UserID", req.UserID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询汇率
	T.ReqQryExchangeRate = function (req, callback) {
		var xr = new ssXmlNode("ReqQryExchangeRate", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//源币种
		xr.set("FromCurrencyID", req.FromCurrencyID);
		//目标币种
		xr.set("ToCurrencyID", req.ToCurrencyID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询二级代理操作员银期权限
	T.ReqQrySecAgentACIDMap = function (req, callback) {
		var xr = new ssXmlNode("ReqQrySecAgentACIDMap", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//用户代码
		xr.set("UserID", req.UserID);
		//资金账户
		xr.set("AccountID", req.AccountID);
		//币种
		xr.set("CurrencyID", req.CurrencyID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询期权交易成本
	T.ReqQryOptionInstrTradeCost = function (req, callback) {
		var xr = new ssXmlNode("ReqQryOptionInstrTradeCost", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//投机套保标志
		if (pQryOptionInstrTradeCost.HedgeFlag > '0')
			xr.set("HedgeFlag", req.HedgeFlag);
		//期权合约报价
		xr.set("InputPrice", req.InputPrice);
		//标的价格,填0则用昨结算价
		xr.set("UnderlyingPrice", req.UnderlyingPrice);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询期权合约手续费
	T.ReqQryOptionInstrCommRate = function (req, callback) {
		var xr = new ssXmlNode("ReqQryOptionInstrCommRate", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询执行宣告
	T.ReqQryExecOrder = function (req, callback) {
		var xr = new ssXmlNode("ReqQryExecOrder", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//执行宣告编号
		xr.set("ExecOrderSysID", req.ExecOrderSysID);
		//开始时间
		xr.set("InsertTimeStart", req.InsertTimeStart);
		//结束时间
		xr.set("InsertTimeEnd", req.InsertTimeEnd);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询询价
	T.ReqQryForQuote = function (req, callback) {
		var xr = new ssXmlNode("ReqQryForQuote", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//开始时间
		xr.set("InsertTimeStart", req.InsertTimeStart);
		//结束时间
		xr.set("InsertTimeEnd", req.InsertTimeEnd);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询报价
	T.ReqQryQuote = function (req, callback) {
		var xr = new ssXmlNode("ReqQryQuote", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//报价编号
		xr.set("QuoteSysID", req.QuoteSysID);
		//开始时间
		xr.set("InsertTimeStart", req.InsertTimeStart);
		//结束时间
		xr.set("InsertTimeEnd", req.InsertTimeEnd);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询监控中心用户令牌
	T.ReqQueryCFMMCTradingAccountToken = function (req, callback) {
		var xr = new ssXmlNode("ReqQueryCFMMCTradingAccountToken", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//发送数据
		this.request(xr, callback);
	}

	//批量报单操作请求
	T.ReqBatchOrderAction = function (req, callback) {
		var xr = new ssXmlNode("ReqBatchOrderAction", this.maxRequestID++);
		//发送数据
		this.request(xr, callback);
	}

	//申请组合录入请求
	T.ReqCombActionInsert = function (req, callback) {
		var xr = new ssXmlNode("ReqCombActionInsert", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//组合引用
		xr.set("CombActionRef", req.CombActionRef);
		//用户代码
		xr.set("UserID", req.UserID);
		//买卖方向
		xr.set("Direction", req.Direction);
		//数量
		xr.set("Volume", req.Volume);
		//组合指令方向
		xr.set("CombDirection", req.CombDirection);
		//投机套保标志
		xr.set("HedgeFlag", req.HedgeFlag);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询产品报价汇率
	T.ReqQryProductExchRate = function (req, callback) {
		var xr = new ssXmlNode("ReqQryProductExchRate", this.maxRequestID++);
		//产品代码
		xr.set("ProductID", req.ProductID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询产品组
	T.ReqQryProductGroup = function (req, callback) {
		var xr = new ssXmlNode("ReqQryProductGroup", this.maxRequestID++);
		//产品代码
		xr.set("ProductID", req.ProductID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询组合合约安全系数
	T.ReqQryCombInstrumentGuard = function (req, callback) {
		var xr = new ssXmlNode("ReqQryCombInstrumentGuard", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//发送数据
		this.request(xr, callback);
	}

	//请求查询申请组合
	T.ReqQryCombAction = function (req, callback) {
		var xr = new ssXmlNode("ReqQryCombAction", this.maxRequestID++);
		//经纪公司代码
		xr.set("BrokerID", req.BrokerID);
		//投资者代码
		xr.set("InvestorID", req.InvestorID);
		//合约代码
		xr.set("InstrumentID", req.InstrumentID);
		//交易所代码
		xr.set("ExchangeID", req.ExchangeID);
		//发送数据
		this.request(xr, callback);
	}

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

	//回调
	T.rspcall = M.rspcall;

	//客户端认证响应
	T.p_OnRspAuthenticate = function (e) {
		var rspInfo = getRspInfo(e);
		var data = new Object();
		//经纪公司代码
		data.BrokerID = getString(e, "BrokerID");
		//用户代码
		data.UserID = getString(e, "UserID");
		//用户端产品信息
		data.UserProductInfo = getString(e, "UserProductInfo");
		//回调
		this.rspcall(data, rspInfo);
	}

	//登录请求响应
	T.p_OnRspUserLogin = M.p_OnRspUserLogin;

	//登出请求响应
	T.p_OnRspUserLogout = M.p_OnRspUserLogout;

	//用户口令更新请求响应
	T.p_OnRspUserPasswordUpdate = function (e) {
		var rspInfo = getRspInfo(e);
		var data = new Object();
		//经纪公司代码
		data.BrokerID = getString(e, "BrokerID");
		//用户代码
		data.UserID = getString(e, "UserID");
		//原来的口令
		data.OldPassword = getString(e, "OldPassword");
		//新的口令
		data.NewPassword = getString(e, "NewPassword");
		//回调
		this.rspcall(data, rspInfo);
	}

	//资金账户口令更新请求响应
	T.p_OnRspTradingAccountPasswordUpdate = nullRsp;

	//报单录入请求响应
	T.p_OnRspOrderInsert = function (e) {
		var rspInfo = getRspInfo(e);
		var data = new Object();
		//经纪公司代码
		data.BrokerID = getString(e, "B");
		//投资者代码
		data.InvestorID = getString(e, "A");
		//合约代码
		data.InstrumentID = getString(e, "I");
		//报单引用
		data.OrderRef = getString(e, "OR");
		//用户代码
		data.UserID = getString(e, "U");
		//报单价格条件
		data.OrderPriceType = getString(e, "OPT");
		//买卖方向
		data.Direction = getString(e, "DF");
		//组合开平标志
		data.CombOffsetFlag = getString(e, "OF");
		//组合投机套保标志
		data.CombHedgeFlag = getString(e, "HF");
		//价格
		data.LimitPrice = getDouble(e, "LP");
		//数量
		data.VolumeTotalOriginal = getString(e, "V");
		//有效期类型
		data.TimeCondition = getString(e, "TC");
		//GTD日期
		data.GTDDate = getString(e, "GD");
		//成交量类型
		data.VolumeCondition = getString(e, "VC");
		//最小成交量
		data.MinVolume = getInt(e, "MV");
		//触发条件
		data.ContingentCondition = getString(e, "CC");
		//止损价
		data.StopPrice = getDouble(e, "SP");
		//止盈价
		data.TargetPrice = getDouble(e, "TP");
		//自动止盈止损类型
		data.AutoStopType = getString(e, "A1");
		//强平原因
		data.ForceCloseReason = getString(e, "FCR");
		//业务单元
		data.BusinessUnit = getString(e, "BU");
		//请求编号
		data.RequestID = getInt(e, "RI");
		//用户强评标志
		data.UserForceClose = getString(e, "UF");
		//互换单标志
		data.IsSwapOrder = getString(e, "SO");
		//回调
		this.rspcall(data, rspInfo);
	}

	//响应持仓自动化操作
	T.p_OnRspPositionAutomation = function (e) {
		var rspInfo = getRspInfo(e);
		var data = new Object();
		//操作编号
		data.ActionID = getString(e, "N");
		//回调
		this.rspcall(data, rspInfo);
	}

	//持仓自动化的通知
	T.p_OnRtnPositionAutomation = function (e) {
		var data = new Object();
		//操作编号
		data.ActionID = getString(e, "N");
		//交易所代码
		data.ExchangeID = getString(e, "E");
		//合约代码
		data.InstrumentID = getString(e, "I");
		//投机套保标志
		data.HedgeFlag = getString(e, "H");
		//卖买方向
		data.Direction = getString(e, "D");
		//止盈价格
		data.TargetPrice = getDoubleN(e, "TP");
		//止盈数量
		data.TargetVolume = getInt(e, "TV");
		//止盈浮动跳数
		data.TargetPlus = getInt(e, "T1");
		//止损价格
		data.StopPrice = getDoubleN(e, "SP");
		//止损数量
		data.StopVolume = getInt(e, "SV");
		//止损浮动跳数
		data.StopPlus = getInt(e, "S1");
		//业务单元
		data.BusinessUnit = getString(e, "BU");
		//发送
		this.OnRtnPositionAutomation(data);
	}

	//预埋单录入请求响应
	T.p_OnRspParkedOrderInsert = nullRsp;

	//预埋撤单录入请求响应
	T.p_OnRspParkedOrderAction = nullRsp;

	//报单操作请求响应
	T.p_OnRspOrderAction = function (e) {
		var rspInfo = getRspInfo(e);
		var data = new Object();

		//经纪公司代码
		data.BrokerID = getString(e, "B");
		//投资者代码
		data.InvestorID = getString(e, "A");
		//报单操作引用
		data.OrderActionRef = getString(e, "AR");
		//报单引用
		data.OrderRef = getString(e, "OR");
		//请求编号
		data.RequestID = getInt(e, "RI");
		//前置编号
		data.FrontID = getInt(e, "F");
		//会话编号
		data.SessionID = getInt(e, "SS");
		//交易所代码
		data.ExchangeID = getString(e, "E");
		//报单编号
		data.OrderSysID = getString(e, "OSI");
		//操作标志
		data.ActionFlag = getString(e, "AF");
		//价格
		data.LimitPrice = getDouble(e, "LP");
		//数量变化
		data.VolumeChange = getString(e, "V");
		//用户代码
		data.UserID = getString(e, "U");
		//合约代码
		data.InstrumentID = getString(e, "I");
		//回调
		this.rspcall(data, rspInfo);
	}

	//查询最大报单数量响应
	T.p_OnRspQueryMaxOrderVolume = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//投资者代码
			data.InvestorID = getString(e, "InvestorID");
			//合约代码
			data.InstrumentID = getString(e, "InstrumentID");
			//买卖方向
			data.Direction = getString(e, "Direction");
			//开平标志
			data.OffsetFlag = getString(e, "OffsetFlag");
			//投机套保标志
			data.HedgeFlag = getString(e, "HedgeFlag");
			//最大允许报单数量
			data.MaxVolume = getInt(e, "MaxVolume");
		}
		this.rspcall(datas, rspInfo);
	}

	//投资者结算结果确认响应
	T.p_OnRspSettlementInfoConfirm = function (e) {
		var rspInfo = getRspInfo(e);
		var data = new Object();

		//经纪公司代码
		data.BrokerID = getString(e, "BrokerID");
		//投资者代码
		data.InvestorID = getString(e, "InvestorID");
		//确认日期
		data.ConfirmDate = getString(e, "ConfirmDate");
		//确认时间
		data.ConfirmTime = getString(e, "ConfirmTime");
		//回调
		this.rspcall(data, rspInfo);
	}

	//删除预埋单响应
	T.p_OnRspRemoveParkedOrder = nullRsp;

	//删除预埋撤单响应
	T.p_OnRspRemoveParkedOrderAction = nullRsp;

	//执行宣告录入请求响应
	T.p_OnRspExecOrderInsert = nullRsp;

	//执行宣告操作请求响应
	T.p_OnRspExecOrderAction = nullRsp;

	//询价录入请求响应
	T.p_OnRspForQuoteInsert = nullRsp;

	//报价录入请求响应
	T.p_OnRspQuoteInsert = nullRsp;

	//报价操作请求响应
	T.p_OnRspQuoteAction = nullRsp;

	//请求查询报单响应
	T.p_OnRspQryOrder = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//读取
			DumpOrder(data, e);
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询成交响应
	T.p_OnRspQryTrade = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//读取
			DumpTrade(data, e);
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询投资者持仓响应
	T.p_OnRspQryInvestorPosition = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		var eRoot = e;
		//循环读取子节点
		for (var ePosition = e.firstChild; ePosition; ePosition = ePosition.nextElementSibling) {
			//单边节点
			for (var eSide = ePosition.firstChild; eSide; eSide = eSide.nextElementSibling) {
				//单个持仓
				for (var eItem = eSide.firstChild; eItem; eItem = eItem.nextElementSibling) {
					e = eItem;
					var data = new Object();
					datas.push(data);
					//经纪公司代码
					data.BrokerID = getString(eRoot, "B");
					//交易日
					data.TradingDay = getString(eRoot, "TD");
					//投资者代码
					data.InvestorID = getString(ePosition, "IN");
					//交易所代码
					data.ExchangeID = getString(ePosition, "E");
					//合约代码
					data.InstrumentID = getString(ePosition, "I");
					//币种代码
					data.CurrencyID = getString(ePosition, "CU");
					//上次结算价
					data.PreSettlementPrice = getDouble(ePosition, "P1");
					//本次结算价
					data.SettlementPrice = getDouble(ePosition, "SP");
					//结算编号
					data.SettlementID = getString(ePosition, "SI");
					//持仓多空方向
					data.PosiDirection = getString(eSide, "D");
					//投机套保标志
					data.HedgeFlag = getString(eSide, "H");
					//保证金率
					data.MarginRateByMoney = getDouble(eSide, "M1");
					//保证金率(按手数)
					data.MarginRateByVolume = getDouble(eSide, "M2");
					//今日持仓
					data.TodayPosition = getInt(eSide, "TP");
					//上日持仓
					data.YdPosition = getInt(eSide, "YP");
					//今日持仓
					data.Position = getInt(e, "P");
					//持仓日期
					data.PositionDate = getString(e, "PD");
					//多头冻结
					data.LongFrozen = getInt(e, "LF");
					//空头冻结
					data.ShortFrozen = getInt(e, "SF");
					//开仓冻结金额
					data.LongFrozenAmount = getDouble(e, "LA");
					//开仓冻结金额
					data.ShortFrozenAmount = getDouble(e, "SA");
					//可平数量
					data.CloseAvailable = getInt(e, "AV");
					//开仓量
					data.OpenVolume = getInt(e, "OV");
					//平仓量
					data.CloseVolume = getInt(e, "CV");
					//开仓金额
					data.OpenAmount = getDouble(e, "OA");
					//平仓金额
					data.CloseAmount = getDouble(e, "CA");
					//持仓成本
					data.PositionCost = getDouble(e, "PC");
					//上次占用的保证金
					data.PreMargin = getDouble(e, "PM");
					//占用的保证金
					data.UseMargin = getDouble(e, "UM");
					//冻结的保证金
					data.FrozenMargin = getDouble(e, "FM");
					//冻结的资金
					data.FrozenCash = getDouble(e, "FC");
					//冻结的手续费
					data.FrozenCommission = getDouble(e, "FS");
					//资金差额
					data.CashIn = getDouble(e, "CI");
					//手续费
					data.Commission = getDouble(e, "C");
					//平仓盈亏
					data.CloseProfit = getDouble(e, "CP");
					//持仓盈亏
					data.PositionProfit = getDouble(e, "PP");
					//开仓成本
					data.OpenCost = getDouble(e, "OC");
					//持仓均价
					data.AverageOpenPrice = getDouble(e, "AO");
					//逐笔对冲持仓均价
					data.AverageOpenPriceByTrade = getDouble(e, "AOT");
					//交易所保证金
					data.ExchangeMargin = getDouble(e, "EM");
					//组合成交形成的持仓
					data.CombPosition = getString(e, "CBP");
					//组合多头冻结
					data.CombLongFrozen = getDouble(e, "CBL");
					//组合空头冻结
					data.CombShortFrozen = getDouble(e, "CBS");
					//逐日盯市平仓盈亏
					data.CloseProfitByDate = getDouble(e, "CPD");
					//逐笔对冲平仓盈亏
					data.CloseProfitByTrade = getDouble(e, "CPT");
					//执行冻结
					data.StrikeFrozen = getDouble(e, "STF");
					//执行冻结金额
					data.StrikeFrozenAmount = getDouble(e, "STA");
					//放弃执行冻结
					data.AbandonFrozen = getDouble(e, "AF");
					//借款金额
					data.CurrLoan = getDouble(e, "CL");
					//冻结的借款金额
					data.FrozenLoan = getDouble(e, "FL");
				}
			}//for2
		}//for1
		this.rspcall(datas, rspInfo);
	}

	//请求查询资金账户响应
	T.p_OnRspQryTradingAccount = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//投资者帐号
			data.AccountID = getString(e, "AccountID");
			//上次质押金额
			data.PreMortgage = getDouble(e, "PM1");
			//上次信用额度
			data.PreCredit = getDouble(e, "PC");
			//上次存款额
			data.PreDeposit = getDouble(e, "PD");
			//上次结算准备金
			data.PreBalance = getDouble(e, "PreBalance");
			//上次占用的保证金
			data.PreMargin = getDouble(e, "PM");
			//利息基数
			data.InterestBase = getDouble(e, "IB");
			//利息收入
			data.Interest = getDouble(e, "Interest");
			//入金金额
			data.Deposit = getDouble(e, "Deposit");
			//出金金额
			data.Withdraw = getDouble(e, "Withdraw");
			//冻结的保证金
			data.FrozenMargin = getDouble(e, "FrozenMargin");
			//冻结的资金
			data.FrozenCash = getDouble(e, "FrozenCash");
			//冻结的手续费
			data.FrozenCommission = getDouble(e, "FrozenCommission");
			//当前保证金总额
			data.CurrMargin = getDouble(e, "CurrMargin");
			//资金差额
			data.CashIn = getDouble(e, "CashIn");
			//手续费
			data.Commission = getDouble(e, "Commission");
			//平仓盈亏
			data.CloseProfit = getDouble(e, "CloseProfit");
			//持仓盈亏
			data.PositionProfit = getDouble(e, "PositionProfit");
			//逐笔对冲平仓盈亏
			data.CloseProfitByTrade = getDouble(e, "CPT");
			//逐笔对冲持仓盈亏
			data.PositionProfitByTrade = getDouble(e, "PPT");
			//期货结算准备金
			data.Balance = getDouble(e, "Balance");
			//可用资金
			data.Available = getDouble(e, "Available");
			//可取资金
			data.WithdrawQuota = getDouble(e, "WithdrawQuota");
			//基本准备金
			data.Reserve = getDouble(e, "RV");
			//交易日
			data.TradingDay = getString(e, "TradingDay");
			//结算编号
			data.SettlementID = getString(e, "SI");
			//借款金额
			data.CurrLoan = getDouble(e, "CL");
			//冻结借款金额
			data.FrozenLoan = getDouble(e, "FL");
			//信用额度
			data.Credit = getDouble(e, "Credit");
			//质押金额
			data.Mortgage = getDouble(e, "MT");
			//交易所保证金
			data.ExchangeMargin = getDouble(e, "EM");
			//投资者交割保证金
			data.DeliveryMargin = getDouble(e, "DM");
			//交易所交割保证金
			data.ExchangeDeliveryMargin = getDouble(e, "EDM");
			//保底期货结算准备金
			data.ReserveBalance = getDouble(e, "RB");
			//币种代码
			data.CurrencyID = getString(e, "CurrencyID");
			//上次货币质入金额
			data.PreFundMortgageIn = getDouble(e, "PMI");
			//上次货币质出金额
			data.PreFundMortgageOut = getDouble(e, "PMO");
			//货币质入金额
			data.FundMortgageIn = getDouble(e, "MI");
			//货币质出金额
			data.FundMortgageOut = getDouble(e, "MO");
			//货币质押余额
			data.FundMortgageAvailable = getDouble(e, "MA");
			//可质押货币金额
			data.MortgageableFund = getDouble(e, "MF");
			//特殊产品占用保证金
			data.SpecProductMargin = getDouble(e, "SM");
			//特殊产品冻结保证金
			data.SpecProductFrozenMargin = getDouble(e, "SFM");
			//特殊产品手续费
			data.SpecProductCommission = getDouble(e, "SC");
			//特殊产品冻结手续费
			data.SpecProductFrozenCommission = getDouble(e, "SFC");
			//特殊产品持仓盈亏
			data.SpecProductPositionProfit = getDouble(e, "SPP");
			//特殊产品平仓盈亏
			data.SpecProductCloseProfit = getDouble(e, "SCP");
			//根据持仓盈亏算法计算的特殊产品持仓盈亏
			data.SpecProductPositionProfitByAlg = getDouble(e, "SPA");
			//特殊产品交易所保证金
			data.SpecProductExchangeMargin = getDouble(e, "SEM");
			//买入额
			data.BuyAmount = getDouble(e, "BA");
			//卖出额
			data.SellAmount = getDouble(e, "SA");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询投资者响应
	T.p_OnRspQryInvestor = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//投资者代码
			data.InvestorID = getString(e, "InvestorID");
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//投资者分组代码
			data.InvestorGroupID = getString(e, "InvestorGroupID");
			//投资者名称
			data.InvestorName = getString(e, "InvestorName");
			//证件类型
			data.IdentifiedCardType = getString(e, "IdentifiedCardType");
			//证件号码
			data.IdentifiedCardNo = getString(e, "IdentifiedCardNo");
			//是否活跃
			data.IsActive = getString(e, "IsActive");
			//联系电话
			data.Telephone = getString(e, "Telephone");
			//通讯地址
			data.Address = getString(e, "Address");
			//开户日期
			data.OpenDate = getString(e, "OpenDate");
			//手机
			data.Mobile = getString(e, "Mobile");
			//手续费率模板代码
			data.CommModelID = getString(e, "CommModelID");
			//保证金率模板代码
			data.MarginModelID = getString(e, "MarginModelID");
			//币种代码
			data.CurrencyID = getString(e, "CurrencyID");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询交易编码响应
	T.p_OnRspQryTradingCode = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//投资者代码
			data.InvestorID = getString(e, "InvestorID");
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//交易所代码
			data.ExchangeID = getString(e, "ExchangeID");
			//客户代码
			data.ClientID = getString(e, "ClientID");
			//是否活跃
			data.IsActive = getString(e, "IsActive");
			//交易编码类型
			data.ClientIDType = getString(e, "ClientIDType");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询合约保证金率响应
	T.p_OnRspQryInstrumentMarginRate = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//合约代码
			data.InstrumentID = getString(e, "I");
			//投资者范围
			data.InvestorRange = getString(e, "IR");
			//经纪公司代码
			data.BrokerID = getString(e, "B");
			//投资者代码
			data.InvestorID = getString(e, "IN");
			//投机套保标志
			data.HedgeFlag = getString(e, "H");
			//多头保证金率
			data.LongMarginRatioByMoney = getDouble(e, "LM");
			//多头保证金费
			data.LongMarginRatioByVolume = getDouble(e, "LV");
			//空头保证金率
			data.ShortMarginRatioByMoney = getDouble(e, "SM");
			//空头保证金费
			data.ShortMarginRatioByVolume = getDouble(e, "SV");
			//是否相对交易所收取
			data.IsRelative = getString(e, "R");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询合约手续费率响应
	T.p_OnRspQryInstrumentCommissionRate = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//合约代码
			data.InstrumentID = getString(e, "I");
			//投资者范围
			data.InvestorRange = getString(e, "IR");
			//经纪公司代码
			data.BrokerID = getString(e, "B");
			//投资者代码
			data.InvestorID = getString(e, "IN");
			//投机套保标志
			data.HedgeFlag = getString(e, "H");
			//开仓手续费率
			data.OpenRatioByMoney = getDouble(e, "OM");
			//开仓手续费
			data.OpenRatioByVolume = getDouble(e, "OV");
			//平仓手续费率
			data.CloseRatioByMoney = getDouble(e, "CM");
			//平仓手续费
			data.CloseRatioByVolume = getDouble(e, "CV");
			//平今手续费率
			data.CloseTodayRatioByMoney = getDouble(e, "TM");
			//平今手续费
			data.CloseTodayRatioByVolume = getDouble(e, "TV");
			//隔夜利率
			data.OvernightRatioByMoney = getDouble(e, "NM");
			//隔夜利息
			data.OvernightRatioByVolume = getDouble(e, "NV");
			//最低单笔手续费
			data.LeastRatioByVolume = getDouble(e, "L");
			//过户费费率
			data.TransferRatioByMoney = getDouble(e, "T");
			//印花税费率
			data.StampsRatioByMoney = getDouble(e, "S");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询交易所响应
	T.p_OnRspQryExchange = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//交易所代码
			data.ExchangeID = getString(e, "ExchangeID");
			//交易所名称
			data.ExchangeName = getString(e, "ExchangeName");
			//交易所属性
			data.ExchangeProperty = getString(e, "ExchangeProperty");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询产品响应
	T.p_OnRspQryProduct = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//产品代码
			data.ProductID = getString(e, "ProductID");
			//产品名称
			data.ProductName = getString(e, "ProductName");
			//交易所代码
			data.ExchangeID = getString(e, "ExchangeID");
			//产品类型
			data.ProductClass = getString(e, "ProductClass");
			//合约数量乘数
			data.VolumeMultiple = getInt(e, "VolumeMultiple");
			//最小变动价位
			data.PriceTick = getDouble(e, "PriceTick");
			//市价单最大下单量
			data.MaxMarketOrderVolume = getInt(e, "MaxMarketOrderVolume");
			//市价单最小下单量
			data.MinMarketOrderVolume = getInt(e, "MinMarketOrderVolume");
			//限价单最大下单量
			data.MaxLimitOrderVolume = getInt(e, "MaxLimitOrderVolume");
			//限价单最小下单量
			data.MinLimitOrderVolume = getInt(e, "MinLimitOrderVolume");
			//持仓类型
			data.PositionType = getString(e, "PositionType");
			//持仓日期类型
			data.PositionDateType = getString(e, "PositionDateType");
			//平仓处理类型
			data.CloseDealType = getString(e, "CloseDealType");
			//交易币种类型
			data.TradeCurrencyID = getString(e, "TradeCurrencyID");
			//质押资金可用范围
			data.MortgageFundUseRange = getString(e, "MortgageFundUseRange");
			//交易所产品代码
			data.ExchangeProductID = getString(e, "ExchangeProductID");
			//合约基础商品乘数
			data.UnderlyingMultiple = getInt(e, "UnderlyingMultiple");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询合约响应
	T.p_OnRspQryInstrument = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//合约代码
			data.InstrumentID = getString(e, "I");
			//交易所代码
			data.ExchangeID = getString(e, "E");
			//合约id
			data.id = data.ExchangeID + "," + data.InstrumentID;
			//合约名称
			data.InstrumentName = getString(e, "IN");
			//合约在交易所的代码
			data.ExchangeInstID = getString(e, "EI");
			//产品代码
			data.ProductID = getString(e, "P");
			//产品类型
			data.ProductClass = getString(e, "PC");
			//交割年份
			data.DeliveryYear = getInt(e, "DY");
			//交割月
			data.DeliveryMonth = getInt(e, "DM");
			//市价单最大下单量
			data.MaxMarketOrderVolume = getInt(e, "UOV");
			//市价单最小下单量
			data.MinMarketOrderVolume = getInt(e, "LOV");
			//限价单最大下单量
			data.MaxLimitOrderVolume = getInt(e, "ULV");
			//限价单最小下单量
			data.MinLimitOrderVolume = getInt(e, "LLV");
			//合约数量乘数
			data.VolumeMultiple = getInt(e, "VM");
			//最小变动价位
			data.PriceTick = getDouble(e, "PT");
			//创建日
			data.CreateDate = getString(e, "CD");
			//上市日
			data.OpenDate = getString(e, "OD");
			//到期日
			data.ExpireDate = getString(e, "EX");
			//开始交割日
			data.StartDelivDate = getString(e, "SE");
			//结束交割日
			data.EndDelivDate = getString(e, "ED");
			//合约生命周期状态
			data.InstLifePhase = getString(e, "LP");
			//当前是否交易
			data.IsTrading = getString(e, "T");
			//持仓类型
			data.PositionType = getString(e, "PX");
			//持仓日期类型
			data.PositionDateType = getString(e, "DT");
			//多头保证金率
			data.LongMarginRatio = getDouble(e, "LM");
			//空头保证金率
			data.ShortMarginRatio = getDouble(e, "SM");
			//是否使用大额单边保证金算法
			data.MaxMarginSideAlgorithm = getString(e, "AG");
			//基础商品代码
			data.UnderlyingInstrID = getString(e, "U");
			//基础商品交易所
			data.UnderlyingExchID = getString(e, "UE");
			//执行价
			data.StrikePrice = getDouble(e, "SP");
			//期权类型
			data.OptionsType = getString(e, "OT");
			//合约基础商品乘数
			data.UnderlyingMultiple = getString(e, "UM");
			//组合类型
			data.CombinationType = getString(e, "CT");
			//币种代码
			data.CurrencyID = getString(e, "C");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询行情响应
	T.p_OnRspQryDepthMarketData = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
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
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询投资者结算结果响应
	T.p_OnRspQrySettlementInfo = function (e) {
		var rspInfo = getRspInfo(e);
		var data = new Object();
		e = e.firstChild;
		if (e) {
			//交易日
			data.TradingDay = getString(e, "TradingDay");
			//结算编号
			data.SettlementID = getString(e, "SettlementID");
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//投资者代码
			data.InvestorID = getString(e, "InvestorID");
			//序号
			data.SequenceNo = getString(e, "SequenceNo");
			//消息正文
			data.Content = getString(e, "Content");
		}
		//回调
		this.rspcall(data, rspInfo);
	}

	//请求查询转帐银行响应
	T.p_OnRspQryTransferBank = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//银行代码
			data.BankID = getString(e, "BankID");
			//银行分中心代码
			data.BankBrchID = getString(e, "BankBrchID");
			//银行名称
			data.BankName = getString(e, "BankName");
			//是否活跃
			data.IsActive = getString(e, "IsActive");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询投资者持仓明细响应
	T.p_OnRspQryInvestorPositionDetail = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		//循环读取子节点
		for (var ePosition = e.firstChild; ePosition; ePosition = ePosition.nextElementSibling) {
			e = ePosition;
			//单边节点
			for (var eSide = ePosition.firstChild; eSide; eSide = eSide.nextElementSibling) {
				e = eSide;
				//条目节点
				for (var eItem = eSide.firstChild; eItem; eItem = eItem.nextElementSibling) {
					e = eItem;
					var data = new Object();
					datas.push(data);
					//经纪公司代码
					data.BrokerID = getString(e, "B");
					//交易日
					data.TradingDay = getString(e, "TD");
					//投资者代码
					data.InvestorID = getString(ePosition, "IN");
					//合约代码
					data.InstrumentID = getString(ePosition, "I");
					//组合合约代码
					data.CombInstrumentID = getString(e, "CI");
					//币种代码
					data.CurrencyID = getString(ePosition, "CU");
					//交易所代码
					data.ExchangeID = getString(ePosition, "E");
					//合约乘数
					data.VolumeMultiple = getInt(ePosition, "VM");
					//结算价
					data.SettlementPrice = getDoubleN(ePosition, "SP");
					//投机套保标志
					data.HedgeFlag = getString(eSide, "H");
					//买卖
					data.Direction = getString(eSide, "D");
					//保证金率
					data.MarginRateByMoney = getDouble(e, "RM");
					//保证金率(按手数)
					data.MarginRateByVolume = getDouble(eSide, "RV");
					//开仓日期
					data.OpenDate = getString(e, "OD");
					//开仓时间
					data.OpenTime = getDateTime(e, "OT");
					//成交编号
					data.TradeID = getString(e, "TI");
					//数量
					data.Volume = getInt(e, "V");
					//开仓价
					data.OpenPrice = getDouble(e, "OP");
					//开仓行权价
					data.OpenStrikePrice = getDoubleN(e, "OS");
					//平仓行权价
					data.CloseStrikePrice = getDoubleN(e, "CS");
					//结算编号
					data.SettlementID = getString(e, "SI");
					//成交类型
					data.TradeType = getString(e, "TT");
					//平仓时间
					data.CloseTime = getDateTime(e, "ET");
					//逐日盯市平仓盈亏
					data.CloseProfitByDate = getDouble(e, "CD");
					//逐笔对冲平仓盈亏
					data.CloseProfitByTrade = getDouble(e, "CT");
					//逐日盯市持仓盈亏
					data.PositionProfitByDate = getDouble(e, "PD");
					//逐笔对冲持仓盈亏
					data.PositionProfitByTrade = getDouble(e, "PT");
					//投资者保证金
					data.Margin = getDouble(e, "M");
					//交易所保证金
					data.ExchMargin = getDouble(e, "EM");
					//平仓量
					data.CloseVolume = getInt(e, "CV");
					//平仓金额
					data.CloseAmount = getDouble(e, "CA");
					//昨结算价
					data.LastSettlementPrice = getDouble(e, "LSP");
					//手续费
					data.Commission = getDouble(e, "C");
					//业务单元
					data.BusinessUnit = getString(e, "BU");
					//盈利分成
					data.ProfitShare = getDouble(e, "PS");
					//借款金额
					data.CurrLoan = getDouble(e, "CL");
				}//for3
			}//for2
		}//for1
		this.rspcall(datas, rspInfo);
	}

	//请求查询客户通知响应
	T.p_OnRspQryNotice = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//消息正文
			data.Content = getString(e, "Content");
			//经纪公司通知内容序列号
			data.SequenceLabel = getString(e, "SequenceLabel");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询结算信息确认响应
	T.p_OnRspQrySettlementInfoConfirm = function (e) {
		var rspInfo = getRspInfo(e);
		var data = new Object();
		if (rspInfo.ErrorID == 0) {
			e = e.firstChild;
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//投资者代码
			data.InvestorID = getString(e, "InvestorID");
			//确认日期
			data.ConfirmDate = getString(e, "ConfirmDate");
			//确认时间
			data.ConfirmTime = getString(e, "ConfirmTime");
		}
		//回调
		this.rspcall(data, rspInfo);
	}

	//请求查询投资者持仓明细响应
	T.p_OnRspQryInvestorPositionCombineDetail = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//交易日
			data.TradingDay = getString(e, "TradingDay");
			//开仓日期
			data.OpenDate = getString(e, "OpenDate");
			//交易所代码
			data.ExchangeID = getString(e, "ExchangeID");
			//结算编号
			data.SettlementID = getString(e, "SettlementID");
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//投资者代码
			data.InvestorID = getString(e, "InvestorID");
			//组合编号
			data.ComTradeID = getString(e, "ComTradeID");
			//撮合编号
			data.TradeID = getString(e, "TradeID");
			//合约代码
			data.InstrumentID = getString(e, "InstrumentID");
			//投机套保标志
			data.HedgeFlag = getString(e, "HedgeFlag");
			//买卖
			data.Direction = getString(e, "Direction");
			//持仓量
			data.TotalAmt = getInt(e, "TotalAmt");
			//投资者保证金
			data.Margin = getDouble(e, "Margin");
			//交易所保证金
			data.ExchMargin = getDouble(e, "ExchMargin");
			//保证金率
			data.MarginRateByMoney = getDouble(e, "MarginRateByMoney");
			//保证金率(按手数)
			data.MarginRateByVolume = getDouble(e, "MarginRateByVolume");
			//单腿编号
			data.LegID = getString(e, "LegID");
			//单腿乘数
			data.LegMultiple = getInt(e, "LegMultiple");
			//组合持仓合约编码
			data.CombInstrumentID = getString(e, "CombInstrumentID");
			//成交组号
			data.TradeGroupID = getString(e, "TradeGroupID");
		}
		this.rspcall(datas, rspInfo);
	}

	//查询保证金监管系统经纪公司资金账户密钥响应
	T.p_OnRspQryCFMMCTradingAccountKey = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//经纪公司统一编码
			data.ParticipantID = getString(e, "ParticipantID");
			//投资者帐号
			data.AccountID = getString(e, "AccountID");
			//密钥编号
			data.KeyID = getString(e, "KeyID");
			//动态密钥
			data.CurrentKey = getString(e, "CurrentKey");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询仓单折抵信息响应
	T.p_OnRspQryEWarrantOffset = nullRsp;

	//请求查询投资者品种/跨品种保证金响应
	T.p_OnRspQryInvestorProductGroupMargin = nullRsp;

	//请求查询交易所保证金率响应
	T.p_OnRspQryExchangeMarginRate = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//合约代码
			data.InstrumentID = getString(e, "InstrumentID");
			//投机套保标志
			data.HedgeFlag = getString(e, "HedgeFlag");
			//多头保证金率
			data.LongMarginRatioByMoney = getDouble(e, "LongMarginRatioByMoney");
			//多头保证金费
			data.LongMarginRatioByVolume = getDouble(e, "LongMarginRatioByVolume");
			//空头保证金率
			data.ShortMarginRatioByMoney = getDouble(e, "ShortMarginRatioByMoney");
			//空头保证金费
			data.ShortMarginRatioByVolume = getDouble(e, "ShortMarginRatioByVolume");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询交易所调整保证金率响应
	T.p_OnRspQryExchangeMarginRateAdjust = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//合约代码
			data.InstrumentID = getString(e, "InstrumentID");
			//投机套保标志
			data.HedgeFlag = getString(e, "HedgeFlag");
			//跟随交易所投资者多头保证金率
			data.LongMarginRatioByMoney = getDouble(e, "LongMarginRatioByMoney");
			//跟随交易所投资者多头保证金费
			data.LongMarginRatioByVolume = getDouble(e, "LongMarginRatioByVolume");
			//跟随交易所投资者空头保证金率
			data.ShortMarginRatioByMoney = getDouble(e, "ShortMarginRatioByMoney");
			//跟随交易所投资者空头保证金费
			data.ShortMarginRatioByVolume = getDouble(e, "ShortMarginRatioByVolume");
			//交易所多头保证金率
			data.ExchLongMarginRatioByMoney = getDouble(e, "ExchLongMarginRatioByMoney");
			//交易所多头保证金费
			data.ExchLongMarginRatioByVolume = getDouble(e, "ExchLongMarginRatioByVolume");
			//交易所空头保证金率
			data.ExchShortMarginRatioByMoney = getDouble(e, "ExchShortMarginRatioByMoney");
			//交易所空头保证金费
			data.ExchShortMarginRatioByVolume = getDouble(e, "ExchShortMarginRatioByVolume");
			//不跟随交易所投资者多头保证金率
			data.NoLongMarginRatioByMoney = getDouble(e, "NoLongMarginRatioByMoney");
			//不跟随交易所投资者多头保证金费
			data.NoLongMarginRatioByVolume = getDouble(e, "NoLongMarginRatioByVolume");
			//不跟随交易所投资者空头保证金率
			data.NoShortMarginRatioByMoney = getDouble(e, "NoShortMarginRatioByMoney");
			//不跟随交易所投资者空头保证金费
			data.NoShortMarginRatioByVolume = getDouble(e, "NoShortMarginRatioByVolume");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询汇率响应
	T.p_OnRspQryExchangeRate = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//源币种
			data.FromCurrencyID = getString(e, "FromCurrencyID");
			//源币种单位数量
			data.FromCurrencyUnit = getString(e, "FromCurrencyUnit");
			//目标币种
			data.ToCurrencyID = getString(e, "ToCurrencyID");
			//汇率
			data.ExchangeRate = getDouble(e, "ExchangeRate");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询二级代理操作员银期权限响应
	T.p_OnRspQrySecAgentACIDMap = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//用户代码
			data.UserID = getString(e, "UserID");
			//资金账户
			data.AccountID = getString(e, "AccountID");
			//币种
			data.CurrencyID = getString(e, "CurrencyID");
			//境外中介机构资金帐号
			data.BrokerSecAgentID = getString(e, "BrokerSecAgentID");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询期权交易成本响应
	T.p_OnRspQryOptionInstrTradeCost = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//投资者代码
			data.InvestorID = getString(e, "InvestorID");
			//合约代码
			data.InstrumentID = getString(e, "InstrumentID");
			//投机套保标志
			data.HedgeFlag = getString(e, "HedgeFlag");
			//期权合约保证金不变部分
			data.FixedMargin = getDouble(e, "FixedMargin");
			//期权合约最小保证金
			data.MiniMargin = getDouble(e, "MiniMargin");
			//期权合约权利金
			data.Royalty = getDouble(e, "Royalty");
			//交易所期权合约保证金不变部分
			data.ExchFixedMargin = getDouble(e, "ExchFixedMargin");
			//交易所期权合约最小保证金
			data.ExchMiniMargin = getDouble(e, "ExchMiniMargin");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询期权合约手续费响应
	T.p_OnRspQryOptionInstrCommRate = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//合约代码
			data.InstrumentID = getString(e, "InstrumentID");
			//投资者范围
			data.InvestorRange = getString(e, "InvestorRange");
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//投资者代码
			data.InvestorID = getString(e, "InvestorID");
			//开仓手续费率
			data.OpenRatioByMoney = getDouble(e, "OpenRatioByMoney");
			//开仓手续费
			data.OpenRatioByVolume = getDouble(e, "OpenRatioByVolume");
			//平仓手续费率
			data.CloseRatioByMoney = getDouble(e, "CloseRatioByMoney");
			//平仓手续费
			data.CloseRatioByVolume = getDouble(e, "CloseRatioByVolume");
			//平今手续费率
			data.CloseTodayRatioByMoney = getDouble(e, "CloseTodayRatioByMoney");
			//平今手续费
			data.CloseTodayRatioByVolume = getDouble(e, "CloseTodayRatioByVolume");
			//执行手续费率
			data.StrikeRatioByMoney = getDouble(e, "StrikeRatioByMoney");
			//执行手续费
			data.StrikeRatioByVolume = getDouble(e, "StrikeRatioByVolume");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询执行宣告响应
	T.p_OnRspQryExecOrder = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//投资者代码
			data.InvestorID = getString(e, "InvestorID");
			//合约代码
			data.InstrumentID = getString(e, "InstrumentID");
			//执行宣告引用
			data.ExecOrderRef = getString(e, "ExecOrderRef");
			//用户代码
			data.UserID = getString(e, "UserID");
			//数量
			data.Volume = getInt(e, "Volume");
			//请求编号
			data.RequestID = getInt(e, "RequestID");
			//业务单元
			data.BusinessUnit = getString(e, "BusinessUnit");
			//开平标志
			data.OffsetFlag = getString(e, "OffsetFlag");
			//投机套保标志
			data.HedgeFlag = getString(e, "HedgeFlag");
			//执行类型
			data.ActionType = getString(e, "ActionType");
			//保留头寸申请的持仓方向
			data.PosiDirection = getString(e, "PosiDirection");
			//期权行权后是否保留期货头寸的标记
			data.ReservePositionFlag = getString(e, "ReservePositionFlag");
			//期权行权后生成的头寸是否自动平仓
			data.CloseFlag = getString(e, "CloseFlag");
			//本地执行宣告编号
			data.ExecOrderLocalID = getString(e, "ExecOrderLocalID");
			//交易所代码
			data.ExchangeID = getString(e, "ExchangeID");
			//会员代码
			data.ParticipantID = getString(e, "ParticipantID");
			//客户代码
			data.ClientID = getString(e, "ClientID");
			//合约在交易所的代码
			data.ExchangeInstID = getString(e, "ExchangeInstID");
			//交易所交易员代码
			data.TraderID = getString(e, "TraderID");
			//安装编号
			data.InstallID = getString(e, "InstallID");
			//执行宣告提交状态
			data.OrderSubmitStatus = getString(e, "OrderSubmitStatus");
			//报单提示序号
			data.NotifySequence = getString(e, "NotifySequence");
			//交易日
			data.TradingDay = getString(e, "TradingDay");
			//结算编号
			data.SettlementID = getString(e, "SettlementID");
			//执行宣告编号
			data.ExecOrderSysID = getString(e, "ExecOrderSysID");
			//报单日期
			data.InsertDate = getString(e, "InsertDate");
			//插入时间
			data.InsertTime = getString(e, "InsertTime");
			//撤销时间
			data.CancelTime = getString(e, "CancelTime");
			//执行结果
			data.ExecResult = getString(e, "ExecResult");
			//结算会员编号
			data.ClearingPartID = getString(e, "ClearingPartID");
			//序号
			data.SequenceNo = getString(e, "SequenceNo");
			//前置编号
			data.FrontID = getInt(e, "FrontID");
			//会话编号
			data.SessionID = getInt(e, "SessionID");
			//用户端产品信息
			data.UserProductInfo = getString(e, "UserProductInfo");
			//状态信息
			data.StatusMsg = getString(e, "StatusMsg");
			//操作用户代码
			data.ActiveUserID = getString(e, "ActiveUserID");
			//经纪公司报单编号
			data.BrokerExecOrderSeq = getString(e, "BrokerExecOrderSeq");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询询价响应
	T.p_OnRspQryForQuote = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//投资者代码
			data.InvestorID = getString(e, "InvestorID");
			//合约代码
			data.InstrumentID = getString(e, "InstrumentID");
			//询价引用
			data.ForQuoteRef = getString(e, "ForQuoteRef");
			//用户代码
			data.UserID = getString(e, "UserID");
			//本地询价编号
			data.ForQuoteLocalID = getString(e, "ForQuoteLocalID");
			//交易所代码
			data.ExchangeID = getString(e, "ExchangeID");
			//会员代码
			data.ParticipantID = getString(e, "ParticipantID");
			//客户代码
			data.ClientID = getString(e, "ClientID");
			//合约在交易所的代码
			data.ExchangeInstID = getString(e, "ExchangeInstID");
			//交易所交易员代码
			data.TraderID = getString(e, "TraderID");
			//安装编号
			data.InstallID = getString(e, "InstallID");
			//报单日期
			data.InsertDate = getString(e, "InsertDate");
			//插入时间
			data.InsertTime = getString(e, "InsertTime");
			//询价状态
			data.ForQuoteStatus = getString(e, "ForQuoteStatus");
			//前置编号
			data.FrontID = getInt(e, "FrontID");
			//会话编号
			data.SessionID = getInt(e, "SessionID");
			//状态信息
			data.StatusMsg = getString(e, "StatusMsg");
			//操作用户代码
			data.ActiveUserID = getString(e, "ActiveUserID");
			//经纪公司询价编号
			data.BrokerForQutoSeq = getString(e, "BrokerForQutoSeq");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询报价响应
	T.p_OnRspQryQuote = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//投资者代码
			data.InvestorID = getString(e, "InvestorID");
			//合约代码
			data.InstrumentID = getString(e, "InstrumentID");
			//报价引用
			data.QuoteRef = getString(e, "QuoteRef");
			//用户代码
			data.UserID = getString(e, "UserID");
			//卖价格
			data.AskPrice = getDouble(e, "AskPrice");
			//买价格
			data.BidPrice = getDouble(e, "BidPrice");
			//卖数量
			data.AskVolume = getInt(e, "AskVolume");
			//买数量
			data.BidVolume = getInt(e, "BidVolume");
			//请求编号
			data.RequestID = getInt(e, "RequestID");
			//业务单元
			data.BusinessUnit = getString(e, "BusinessUnit");
			//卖开平标志
			data.AskOffsetFlag = getString(e, "AskOffsetFlag");
			//买开平标志
			data.BidOffsetFlag = getString(e, "BidOffsetFlag");
			//卖投机套保标志
			data.AskHedgeFlag = getString(e, "AskHedgeFlag");
			//买投机套保标志
			data.BidHedgeFlag = getString(e, "BidHedgeFlag");
			//本地报价编号
			data.QuoteLocalID = getString(e, "QuoteLocalID");
			//交易所代码
			data.ExchangeID = getString(e, "ExchangeID");
			//会员代码
			data.ParticipantID = getString(e, "ParticipantID");
			//客户代码
			data.ClientID = getString(e, "ClientID");
			//合约在交易所的代码
			data.ExchangeInstID = getString(e, "ExchangeInstID");
			//交易所交易员代码
			data.TraderID = getString(e, "TraderID");
			//安装编号
			data.InstallID = getString(e, "InstallID");
			//报价提示序号
			data.NotifySequence = getString(e, "NotifySequence");
			//报价提交状态
			data.OrderSubmitStatus = getString(e, "OrderSubmitStatus");
			//交易日
			data.TradingDay = getString(e, "TradingDay");
			//结算编号
			data.SettlementID = getString(e, "SettlementID");
			//报价编号
			data.QuoteSysID = getString(e, "QuoteSysID");
			//报单日期
			data.InsertDate = getString(e, "InsertDate");
			//插入时间
			data.InsertTime = getString(e, "InsertTime");
			//撤销时间
			data.CancelTime = getString(e, "CancelTime");
			//报价状态
			data.QuoteStatus = getString(e, "QuoteStatus");
			//结算会员编号
			data.ClearingPartID = getString(e, "ClearingPartID");
			//序号
			data.SequenceNo = getString(e, "SequenceNo");
			//卖方报单编号
			data.AskOrderSysID = getString(e, "AskOrderSysID");
			//买方报单编号
			data.BidOrderSysID = getString(e, "BidOrderSysID");
			//前置编号
			data.FrontID = getInt(e, "FrontID");
			//会话编号
			data.SessionID = getInt(e, "SessionID");
			//用户端产品信息
			data.UserProductInfo = getString(e, "UserProductInfo");
			//状态信息
			data.StatusMsg = getString(e, "StatusMsg");
			//操作用户代码
			data.ActiveUserID = getString(e, "ActiveUserID");
			//经纪公司报价编号
			data.BrokerQuoteSeq = getString(e, "BrokerQuoteSeq");
			//衍生卖报单引用
			data.AskOrderRef = getString(e, "AskOrderRef");
			//衍生买报单引用
			data.BidOrderRef = getString(e, "BidOrderRef");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询转帐流水响应
	T.p_OnRspQryTransferSerial = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//平台流水号
			data.PlateSerial = getString(e, "PlateSerial");
			//交易发起方日期
			data.TradeDate = getString(e, "TradeDate");
			//交易日期
			data.TradingDay = getString(e, "TradingDay");
			//交易时间
			data.TradeTime = getString(e, "TradeTime");
			//交易代码
			data.TradeCode = getString(e, "TradeCode");
			//会话编号
			data.SessionID = getInt(e, "SessionID");
			//银行编码
			data.BankID = getString(e, "BankID");
			//银行分支机构编码
			data.BankBranchID = getString(e, "BankBranchID");
			//银行帐号类型
			data.BankAccType = getString(e, "BankAccType");
			//银行帐号
			data.BankAccount = getString(e, "BankAccount");
			//银行流水号
			data.BankSerial = getString(e, "BankSerial");
			//期货公司编码
			data.BrokerID = getString(e, "BrokerID");
			//期商分支机构代码
			data.BrokerBranchID = getString(e, "BrokerBranchID");
			//期货公司帐号类型
			data.FutureAccType = getString(e, "FutureAccType");
			//投资者帐号
			data.AccountID = getString(e, "AccountID");
			//投资者代码
			data.InvestorID = getString(e, "InvestorID");
			//期货公司流水号
			data.FutureSerial = getString(e, "FutureSerial");
			//证件类型
			data.IdCardType = getString(e, "IdCardType");
			//证件号码
			data.IdentifiedCardNo = getString(e, "IdentifiedCardNo");
			//币种代码
			data.CurrencyID = getString(e, "CurrencyID");
			//交易金额
			data.TradeAmount = getString(e, "TradeAmount");
			//应收客户费用
			data.CustFee = getString(e, "CustFee");
			//应收期货公司费用
			data.BrokerFee = getString(e, "BrokerFee");
			//有效标志
			data.AvailabilityFlag = getString(e, "AvailabilityFlag");
			//操作员
			data.OperatorCode = getString(e, "OperatorCode");
			//新银行帐号
			data.BankNewAccount = getString(e, "BankNewAccount");
			//错误代码
			data.ErrorID = getInt(e, "ErrorID");
			//错误信息
			data.ErrorMsg = getString(e, "ErrorMsg");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询银期签约关系响应
	T.p_OnRspQryAccountregister = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//交易日期
			data.TradeDay = getString(e, "TradeDay");
			//银行编码
			data.BankID = getString(e, "BankID");
			//银行分支机构编码
			data.BankBranchID = getString(e, "BankBranchID");
			//银行帐号
			data.BankAccount = getString(e, "BankAccount");
			//期货公司编码
			data.BrokerID = getString(e, "BrokerID");
			//期货公司分支机构编码
			data.BrokerBranchID = getString(e, "BrokerBranchID");
			//投资者帐号
			data.AccountID = getString(e, "AccountID");
			//证件类型
			data.IdCardType = getString(e, "IdCardType");
			//证件号码
			data.IdentifiedCardNo = getString(e, "IdentifiedCardNo");
			//客户姓名
			data.CustomerName = getString(e, "CustomerName");
			//币种代码
			data.CurrencyID = getString(e, "CurrencyID");
			//开销户类别
			data.OpenOrDestroy = getString(e, "OpenOrDestroy");
			//签约日期
			data.RegDate = getString(e, "RegDate");
			//解约日期
			data.OutDate = getString(e, "OutDate");
			//交易ID
			data.TID = getString(e, "TID");
			//客户类型
			data.CustType = getString(e, "CustType");
			//银行帐号类型
			data.BankAccType = getString(e, "BankAccType");
		}
		this.rspcall(datas, rspInfo);
	}

	//处理错误消息
	T.p_OnRspError = function (e) {
		this.OnRspError(getRspInfo(e));
	}

	//处理委托回报
	T.p_OnRtnOrder = function (e) {
		var data = new Object();
		DumpOrder(data, e);
		this.privateTopicIndex = data.SequenceNo;
		this.OnRtnOrder(data);
	}

	//处理成交回报
	T.p_OnRtnTrade = function (e) {
		var data = new Object();
		DumpTrade(data, e);
		this.privateTopicIndex = data.SequenceNo;
		this.OnRtnTrade(data);
	}

	//报单录入错误回报
	T.p_OnErrRtnOrderInsert = nullRsp;

	//报单操作错误回报
	T.p_OnErrRtnOrderAction = nullRsp;

	//合约交易状态通知
	T.p_OnRtnInstrumentStatus = M.p_OnRtnInstrumentStatus;

	//交易通知
	T.p_OnRtnTradingNotice = function (e) {
		var data = new Object();
		//经纪公司代码
		data.BrokerID = getString(e, "BrokerID");
		//投资者代码
		data.InvestorID = getString(e, "InvestorID");
		//发送时间
		data.SendTime = getString(e, "SendTime");
		//消息正文
		data.FieldContent = getString(e, "FieldContent");
		//序列系列号
		data.SequenceSeries = getString(e, "SequenceSeries");
		//序列号
		data.SequenceNo = getString(e, "SequenceNo");
		//回调
		this.OnRtnTradingNotice(data);
	}

	//提示条件单校验错误
	T.p_OnRtnErrorConditionalOrder = nullRsp;

	//执行宣告通知
	T.p_OnRtnExecOrder = function (e) {
		var data = new Object();

		//经纪公司代码
		data.BrokerID = getString(e, "BrokerID");
		//投资者代码
		data.InvestorID = getString(e, "InvestorID");
		//合约代码
		data.InstrumentID = getString(e, "InstrumentID");
		//执行宣告引用
		data.ExecOrderRef = getString(e, "ExecOrderRef");
		//用户代码
		data.UserID = getString(e, "UserID");
		//数量
		data.Volume = getInt(e, "Volume");
		//请求编号
		data.RequestID = getInt(e, "RequestID");
		//业务单元
		data.BusinessUnit = getString(e, "BusinessUnit");
		//开平标志
		data.OffsetFlag = getString(e, "OffsetFlag");
		//投机套保标志
		data.HedgeFlag = getString(e, "HedgeFlag");
		//执行类型
		data.ActionType = getString(e, "ActionType");
		//保留头寸申请的持仓方向
		data.PosiDirection = getString(e, "PosiDirection");
		//期权行权后是否保留期货头寸的标记
		data.ReservePositionFlag = getString(e, "ReservePositionFlag");
		//期权行权后生成的头寸是否自动平仓
		data.CloseFlag = getString(e, "CloseFlag");
		//本地执行宣告编号
		data.ExecOrderLocalID = getString(e, "ExecOrderLocalID");
		//交易所代码
		data.ExchangeID = getString(e, "ExchangeID");
		//会员代码
		data.ParticipantID = getString(e, "ParticipantID");
		//客户代码
		data.ClientID = getString(e, "ClientID");
		//合约在交易所的代码
		data.ExchangeInstID = getString(e, "ExchangeInstID");
		//交易所交易员代码
		data.TraderID = getString(e, "TraderID");
		//安装编号
		data.InstallID = getString(e, "InstallID");
		//执行宣告提交状态
		data.OrderSubmitStatus = getString(e, "OrderSubmitStatus");
		//报单提示序号
		data.NotifySequence = getString(e, "NotifySequence");
		//交易日
		data.TradingDay = getString(e, "TradingDay");
		//结算编号
		data.SettlementID = getString(e, "SettlementID");
		//执行宣告编号
		data.ExecOrderSysID = getString(e, "ExecOrderSysID");
		//报单日期
		data.InsertDate = getString(e, "InsertDate");
		//插入时间
		data.InsertTime = getString(e, "InsertTime");
		//撤销时间
		data.CancelTime = getString(e, "CancelTime");
		//执行结果
		data.ExecResult = getString(e, "ExecResult");
		//结算会员编号
		data.ClearingPartID = getString(e, "ClearingPartID");
		//序号
		data.SequenceNo = getString(e, "SequenceNo");
		//前置编号
		data.FrontID = getInt(e, "FrontID");
		//会话编号
		data.SessionID = getInt(e, "SessionID");
		//用户端产品信息
		data.UserProductInfo = getString(e, "UserProductInfo");
		//状态信息
		data.StatusMsg = getString(e, "StatusMsg");
		//操作用户代码
		data.ActiveUserID = getString(e, "ActiveUserID");
		//经纪公司报单编号
		data.BrokerExecOrderSeq = getString(e, "BrokerExecOrderSeq");
		//回调
		this.OnRtnExecOrder(data);
	}

	//执行宣告录入错误回报
	T.p_OnErrRtnExecOrderInsert = nullRsp;

	//执行宣告操作错误回报
	T.p_OnErrRtnExecOrderAction = nullRsp;

	//询价录入错误回报
	T.p_OnErrRtnForQuoteInsert = nullRsp;

	//报价通知
	T.p_OnRtnQuote = nullRsp;

	//报价录入错误回报
	T.p_OnErrRtnQuoteInsert = nullRsp;

	//报价操作错误回报
	T.p_OnErrRtnQuoteAction = nullRsp;

	//询价通知
	T.p_OnRtnForQuoteRsp = nullRsp;

	//保证金监控中心用户令牌
	T.p_OnRtnCFMMCTradingAccountToken = nullRsp;

	//请求查询签约银行响应
	T.p_OnRspQryContractBank = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//银行代码
			data.BankID = getString(e, "BankID");
			//银行分中心代码
			data.BankBrchID = getString(e, "BankBrchID");
			//银行名称
			data.BankName = getString(e, "BankName");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询预埋单响应
	T.p_OnRspQryParkedOrder = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//投资者代码
			data.InvestorID = getString(e, "InvestorID");
			//合约代码
			data.InstrumentID = getString(e, "InstrumentID");
			//报单引用
			data.OrderRef = getString(e, "OrderRef");
			//用户代码
			data.UserID = getString(e, "UserID");
			//报单价格条件
			data.OrderPriceType = getString(e, "OrderPriceType");
			//买卖方向
			data.Direction = getString(e, "Direction");
			//组合开平标志
			data.CombOffsetFlag = getString(e, "CombOffsetFlag");
			//组合投机套保标志
			data.CombHedgeFlag = getString(e, "CombHedgeFlag");
			//价格
			data.LimitPrice = getDouble(e, "LimitPrice");
			//数量
			data.VolumeTotalOriginal = getString(e, "VolumeTotalOriginal");
			//有效期类型
			data.TimeCondition = getString(e, "TimeCondition");
			//GTD日期
			data.GTDDate = getString(e, "GTDDate");
			//成交量类型
			data.VolumeCondition = getString(e, "VolumeCondition");
			//最小成交量
			data.MinVolume = getInt(e, "MinVolume");
			//触发条件
			data.ContingentCondition = getString(e, "ContingentCondition");
			//止损价
			data.StopPrice = getDouble(e, "StopPrice");
			//强平原因
			data.ForceCloseReason = getString(e, "ForceCloseReason");
			//自动挂起标志
			data.IsAutoSuspend = getString(e, "IsAutoSuspend");
			//业务单元
			data.BusinessUnit = getString(e, "BusinessUnit");
			//请求编号
			data.RequestID = getInt(e, "RequestID");
			//用户强评标志
			data.UserForceClose = getString(e, "UserForceClose");
			//交易所代码
			data.ExchangeID = getString(e, "ExchangeID");
			//预埋报单编号
			data.ParkedOrderID = getString(e, "ParkedOrderID");
			//用户类型
			data.UserType = getString(e, "UserType");
			//预埋单状态
			data.Status = getString(e, "Status");
			//错误代码
			data.ErrorID = getInt(e, "ErrorID");
			//错误信息
			data.ErrorMsg = getString(e, "ErrorMsg");
			//互换单标志
			data.IsSwapOrder = getString(e, "IsSwapOrder");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询预埋撤单响应
	T.p_OnRspQryParkedOrderAction = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//投资者代码
			data.InvestorID = getString(e, "InvestorID");
			//报单操作引用
			data.OrderActionRef = getString(e, "OrderActionRef");
			//报单引用
			data.OrderRef = getString(e, "OrderRef");
			//请求编号
			data.RequestID = getInt(e, "RequestID");
			//前置编号
			data.FrontID = getInt(e, "FrontID");
			//会话编号
			data.SessionID = getInt(e, "SessionID");
			//交易所代码
			data.ExchangeID = getString(e, "ExchangeID");
			//报单编号
			data.OrderSysID = getString(e, "OrderSysID");
			//操作标志
			data.ActionFlag = getString(e, "ActionFlag");
			//价格
			data.LimitPrice = getDouble(e, "LimitPrice");
			//数量变化
			data.VolumeChange = getString(e, "VolumeChange");
			//用户代码
			data.UserID = getString(e, "UserID");
			//合约代码
			data.InstrumentID = getString(e, "InstrumentID");
			//预埋撤单单编号
			data.ParkedOrderActionID = getString(e, "ParkedOrderActionID");
			//用户类型
			data.UserType = getString(e, "UserType");
			//预埋撤单状态
			data.Status = getString(e, "Status");
			//错误代码
			data.ErrorID = getInt(e, "ErrorID");
			//错误信息
			data.ErrorMsg = getString(e, "ErrorMsg");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询交易通知响应
	T.p_OnRspQryTradingNotice = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//投资者范围
			data.InvestorRange = getString(e, "InvestorRange");
			//投资者代码
			data.InvestorID = getString(e, "InvestorID");
			//序列系列号
			data.SequenceSeries = getString(e, "SequenceSeries");
			//用户代码
			data.UserID = getString(e, "UserID");
			//发送时间
			data.SendTime = getString(e, "SendTime");
			//序列号
			data.SequenceNo = getString(e, "SequenceNo");
			//消息正文
			data.FieldContent = getString(e, "FieldContent");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询经纪公司交易参数响应
	T.p_OnRspQryBrokerTradingParams = function (e) {
		var rspInfo = getRspInfo(e);
		var data = new Object();

		//经纪公司代码
		data.BrokerID = getString(e, "BrokerID");
		//投资者代码
		data.InvestorID = getString(e, "InvestorID");
		//保证金价格类型
		data.MarginPriceType = getString(e, "MarginPriceType");
		//盈亏算法
		data.Algorithm = getString(e, "Algorithm");
		//可用是否包含平仓盈利
		data.AvailIncludeCloseProfit = getString(e, "AvailIncludeCloseProfit");
		//币种代码
		data.CurrencyID = getString(e, "CurrencyID");
		//期权权利金价格类型
		data.OptionRoyaltyPriceType = getString(e, "OptionRoyaltyPriceType");
		//回调
		this.rspcall(data, rspInfo);
	}

	//请求查询经纪公司交易算法响应
	T.p_OnRspQryBrokerTradingAlgos = function (e) {
		var rspInfo = getRspInfo(e);
		var datas = new Array();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			var data = new Object();
			datas.push(data);
			//经纪公司代码
			data.BrokerID = getString(e, "BrokerID");
			//交易所代码
			data.ExchangeID = getString(e, "ExchangeID");
			//合约代码
			data.InstrumentID = getString(e, "InstrumentID");
			//持仓处理算法编号
			data.HandlePositionAlgoID = getString(e, "HandlePositionAlgoID");
			//寻找保证金率算法编号
			data.FindMarginRateAlgoID = getString(e, "FindMarginRateAlgoID");
			//资金处理算法编号
			data.HandleTradingAccountAlgoID = getString(e, "HandleTradingAccountAlgoID");
		}
		this.rspcall(datas, rspInfo);
	}

	//请求查询监控中心用户令牌
	T.p_OnRspQueryCFMMCTradingAccountToken = function (e) {
		var rspInfo = getRspInfo(e);
		var data = new Object();
		//经纪公司代码
		data.BrokerID = getString(e, "BrokerID");
		//投资者代码
		data.InvestorID = getString(e, "InvestorID");
		//回调
		this.rspcall(data, rspInfo);
	}

	//转存转账信息字段
	function dumpTransferField(e, data) {
		//业务功能码
		data.TradeCode = getString(e, "TradeCode");
		//银行代码
		data.BankID = getString(e, "BankID");
		//银行分支机构代码
		data.BankBranchID = getString(e, "BankBranchID");
		//期商代码
		data.BrokerID = getString(e, "BrokerID");
		//期商分支机构代码
		data.BrokerBranchID = getString(e, "BrokerBranchID");
		//交易日期
		data.TradeDate = getString(e, "TradeDate");
		//交易时间
		data.TradeTime = getString(e, "TradeTime");
		//银行流水号
		data.BankSerial = getString(e, "BankSerial");
		//交易系统日期 
		data.TradingDay = getString(e, "TradingDay");
		//银期平台消息流水号
		data.PlateSerial = getString(e, "PlateSerial");
		//最后分片标志
		data.LastFragment = getString(e, "LastFragment");
		//会话号
		data.SessionID = getInt(e, "SessionID");
		//客户姓名
		data.CustomerName = getString(e, "CustomerName");
		//证件类型
		data.IdCardType = getString(e, "IdCardType");
		//证件号码
		data.IdentifiedCardNo = getString(e, "IdentifiedCardNo");
		//客户类型
		data.CustType = getString(e, "CustType");
		//银行帐号
		data.BankAccount = getString(e, "BankAccount");
		//银行密码
		data.BankPassWord = getString(e, "BankPassWord");
		//投资者帐号
		data.AccountID = getString(e, "AccountID");
		//期货密码
		data.Password = getString(e, "Password");
		//安装编号
		data.InstallID = getString(e, "InstallID");
		//期货公司流水号
		data.FutureSerial = getString(e, "FutureSerial");
		//用户标识
		data.UserID = getString(e, "UserID");
		//验证客户证件号码标志
		data.VerifyCertNoFlag = getString(e, "VerifyCertNoFlag");
		//币种代码
		data.CurrencyID = getString(e, "CurrencyID");
		//转帐金额
		data.TradeAmount = getDouble(e, "TradeAmount");
		//期货可取金额
		data.FutureFetchAmount = getString(e, "FutureFetchAmount");
		//费用支付标志
		data.FeePayFlag = getString(e, "FeePayFlag");
		//应收客户费用
		data.CustFee = getString(e, "CustFee");
		//应收期货公司费用
		data.BrokerFee = getString(e, "BrokerFee");
		//发送方给接收方的消息
		data.Message = getString(e, "Message");
		//摘要
		data.Digest = getString(e, "Digest");
		//银行帐号类型
		data.BankAccType = getString(e, "BankAccType");
		//渠道标志
		data.DeviceID = getString(e, "DeviceID");
		//期货单位帐号类型
		data.BankSecuAccType = getString(e, "BankSecuAccType");
		//期货公司银行编码
		data.BrokerIDByBank = getString(e, "BrokerIDByBank");
		//期货单位帐号
		data.BankSecuAcc = getString(e, "BankSecuAcc");
		//银行密码标志
		data.BankPwdFlag = getString(e, "BankPwdFlag");
		//期货资金密码核对标志
		data.SecuPwdFlag = getString(e, "SecuPwdFlag");
		//交易柜员
		data.OperNo = getString(e, "OperNo");
		//请求编号
		data.RequestID = getInt(e, "RequestID");
		//交易ID
		data.TID = getString(e, "TID");
		//转账交易状态
		data.TransferStatus = getString(e, "TransferStatus");
		//错误代码
		data.ErrorID = getInt(e, "ErrorID");
		//错误信息
		data.ErrorMsg = getString(e, "ErrorMsg");
	}

	//银行发起银行资金转期货通知
	T.p_OnRtnFromBankToFutureByBank = function (e) {
		var data = new Object();
		//转存
		dumpTransferField(e, data);
		//回调
		this.OnRtnFromBankToFutureByBank(data);
	}

	//银行发起期货资金转银行通知
	T.p_OnRtnFromFutureToBankByBank = function (e) {
		var data = new Object();
		//转存
		dumpTransferField(e, data);
		//回调
		this.OnRtnFromFutureToBankByBank(data);
	}

	//银行发起冲正银行转期货通知
	T.p_OnRtnRepealFromBankToFutureByBank = nullRsp;

	//银行发起冲正期货转银行通知
	T.p_OnRtnRepealFromFutureToBankByBank = nullRsp;

	//期货发起银行资金转期货通知
	T.p_OnRtnFromBankToFutureByFuture = function (e) {
		var data = new Object();
		//转存
		dumpTransferField(e, data);
		//回调
		this.OnRtnFromBankToFutureByFuture(data);
	}

	//期货发起期货资金转银行通知
	T.p_OnRtnFromFutureToBankByFuture = function (e) {
		var data = new Object();
		//转存
		dumpTransferField(e, data);
		//回调
		this.OnRtnFromFutureToBankByFuture(data);
	}

	//系统运行时期货端手工发起冲正银行转期货请求，银行处理完毕后报盘发回的通知
	T.p_OnRtnRepealFromBankToFutureByFutureManual = nullRsp;

	//系统运行时期货端手工发起冲正期货转银行请求，银行处理完毕后报盘发回的通知
	T.p_OnRtnRepealFromFutureToBankByFutureManual = nullRsp;

	//期货发起查询银行余额通知
	T.p_OnRtnQueryBankBalanceByFuture = nullRsp;

	//期货发起银行资金转期货错误回报
	T.p_OnErrRtnBankToFutureByFuture = nullRsp;

	//期货发起期货资金转银行错误回报
	T.p_OnErrRtnFutureToBankByFuture = nullRsp;

	//系统运行时期货端手工发起冲正银行转期货错误回报
	T.p_OnErrRtnRepealBankToFutureByFutureManual = nullRsp;

	//系统运行时期货端手工发起冲正期货转银行错误回报
	T.p_OnErrRtnRepealFutureToBankByFutureManual = nullRsp;

	//期货发起查询银行余额错误回报
	T.p_OnErrRtnQueryBankBalanceByFuture = nullRsp;

	//期货发起冲正银行转期货请求，银行处理完毕后报盘发回的通知
	T.p_OnRtnRepealFromBankToFutureByFuture = nullRsp;

	//期货发起冲正期货转银行请求，银行处理完毕后报盘发回的通知
	T.p_OnRtnRepealFromFutureToBankByFuture = nullRsp;

	//期货发起银行资金转期货应答
	T.p_OnRspFromBankToFutureByFuture = nullRsp;

	//期货发起期货资金转银行应答
	T.p_OnRspFromFutureToBankByFuture = nullRsp;

	//期货发起查询银行余额应答
	T.p_OnRspQueryBankAccountMoneyByFuture = nullRsp;

	//银行发起银期开户通知
	T.p_OnRtnOpenAccountByBank = nullRsp;

	//银行发起银期销户通知
	T.p_OnRtnCancelAccountByBank = nullRsp;

	//银行发起变更银行账号通知
	T.p_OnRtnChangeAccountByBank = nullRsp;

	//账户风控信息改变通知
	T.p_OnRtnAccountRisk = function (e) {
		var data = new Object();
		data.InvestorID = getString(e, "InvestorID");
		data.WarningByBalance = getDouble(e, "WarningByBalance");
		data.WindupByBalance = getDouble(e, "WindupByBalance");
		this.OnRtnAccountRisk(data);
	}

	//合约风控信息改变通知
	T.p_OnRtnInstrumentRisk = function (e) {
		var data = new Object();
		for (e = e.firstChild; e; e = e.nextElementSibling) {
			data.InstrumentID = getString(e, "I");
			data.WarningBalanceByNet = getDouble(e, "WA");
			data.WindupBalanceByNet = getDouble(e, "WU");
			this.OnRtnInstrumentRisk(data);
		}
	}

	//转储委托信息
	function DumpOrder(data, e) {
		//经纪公司代码
		data.BrokerID = getString(e, "B");
		//投资者代码
		data.InvestorID = getString(e, "A");
		//合约代码
		data.InstrumentID = getString(e, "I");
		//币种代码
		data.CurrencyID = getString(e, "CU");
		//报单引用
		data.OrderRef = getString(e, "OR");
		//用户代码
		data.UserID = getString(e, "U");
		//报单价格条件
		data.OrderPriceType = getString(e, "OPT");
		//买卖方向
		data.Direction = getString(e, "DF");
		//组合开平标志
		data.CombOffsetFlag = getString(e, "OF");
		//组合投机套保标志
		data.CombHedgeFlag = getString(e, "HF");
		//价格
		data.LimitPrice = getDouble(e, "LP");
		//数量
		data.VolumeTotalOriginal = getString(e, "V");
		//有效期类型
		data.TimeCondition = getString(e, "TC");
		//GTD日期
		data.GTDDate = getString(e, "GD");
		//成交量类型
		data.VolumeCondition = getString(e, "VC");
		//最小成交量
		data.MinVolume = getInt(e, "MV");
		//触发条件
		data.ContingentCondition = getString(e, "CC");
		//止损价
		data.StopPrice = getDouble(e, "SP");
		//止盈价
		data.TargetPrice = getDouble(e, "TP");
		//止损止盈类型
		data.AutoStopType = getString(e, "A1");
		//报单保证金
		data.Margin = getDouble(e, "M");
		//借款比例
		data.LoanRatio = getDouble(e, "LO");
		//强平原因
		data.ForceCloseReason = getString(e, "FCR");
		//业务单元
		data.BusinessUnit = getString(e, "BU");
		//请求编号
		data.RequestID = getInt(e, "RI");
		//本地报单编号
		data.OrderLocalID = getString(e, "OL");
		//交易所代码
		data.ExchangeID = getString(e, "E");
		//会员代码
		data.ParticipantID = getString(e, "PAI");
		//客户代码
		data.ClientID = getString(e, "CI");
		//合约在交易所的代码
		data.ExchangeInstID = getString(e, "EI");
		//交易所交易员代码
		data.TraderID = getString(e, "ET");
		//安装编号
		data.InstallID = getString(e, "INI");
		//报单提交状态
		data.OrderSubmitStatus = getString(e, "OSS");
		//报单提示序号
		data.NotifySequence = getString(e, "NS");
		//交易日
		data.TradingDay = getString(e, "TD");
		//结算编号
		data.SettlementID = getString(e, "SI");
		//报单编号
		data.OrderSysID = getString(e, "OSI");
		//报单来源
		data.OrderSource = getString(e, "OS");
		//报单状态
		data.OrderStatus = getString(e, "OST");
		//报单类型
		data.OrderType = getString(e, "OT");
		//今成交数量
		data.VolumeTraded = getInt(e, "VT");
		//剩余数量
		data.VolumeTotal = data.VolumeTotalOriginal - data.VolumeTraded;
		//报单日期
		data.InsertDate = getString(e, "ID");
		//委托时间
		data.InsertTime = getString(e, "IT");
		//激活时间
		data.ActiveTime = getString(e, "AT");
		//挂起时间
		data.SuspendTime = getString(e, "ST");
		//最后修改时间
		data.UpdateTime = getString(e, "UT");
		//撤销时间
		data.CancelTime = getString(e, "CT");
		//最后修改交易所交易员代码
		data.ActiveTraderID = getString(e, "ATI");
		//结算会员编号
		data.ClearingPartID = getString(e, "CPI");
		//序号
		data.SequenceNo = getString(e, "XI");
		//前置编号
		data.FrontID = getInt(e, "F");
		//会话编号
		data.SessionID = getInt(e, "SS");
		//用户端产品信息
		data.UserProductInfo = getString(e, "UP");
		//状态信息
		data.StatusMsg = getString(e, "SM");
		//用户强评标志
		data.UserForceClose = getString(e, "UF");
		//操作用户代码
		data.ActiveUserID = getString(e, "AU");
		//经纪公司报单编号
		data.BrokerOrderSeq = getString(e, "BO");
		//相关报单
		data.RelativeOrderSysID = getString(e, "ROSI");
		//郑商所成交数量
		data.ZCETotalTradedVolume = getString(e, "ZV");
		//互换单标志
		data.IsSwapOrder = getString(e, "SO");
	}

	//转储成交信息
	function DumpTrade(data, e) {
		//经纪公司代码
		data.BrokerID = getString(e, "B");
		//投资者代码
		data.InvestorID = getString(e, "A");
		//合约代码
		data.InstrumentID = getString(e, "I");
		//报单引用
		data.OrderRef = getString(e, "OR");
		//用户代码
		data.UserID = getString(e, "U");
		//交易所代码
		data.ExchangeID = getString(e, "E");
		//成交编号
		data.TradeID = getString(e, "TI");
		//买卖方向
		data.Direction = getString(e, "DF");
		//报单编号
		data.OrderSysID = getString(e, "OSI");
		//会员代码
		data.ParticipantID = getString(e, "PAI");
		//客户代码
		data.ClientID = getString(e, "CI");
		//交易角色
		data.TradingRole = getString(e, "TR");
		//合约在交易所的代码
		data.ExchangeInstID = getString(e, "EI");
		//开平标志
		data.OffsetFlag = getString(e, "OF");
		//投机套保标志
		data.HedgeFlag = getString(e, "HF");
		//价格
		data.Price = getDouble(e, "P");
		//行权价
		data.StrikePrice = getDoubleN(e, "SK");
		//数量
		data.Volume = getInt(e, "V");
		//成交时期
		data.TradeDate = getString(e, "D");
		//成交时间
		data.TradeTime = getString(e, "T");
		//成交类型
		data.TradeType = getString(e, "TT");
		//成交价来源
		data.PriceSource = getString(e, "PS");
		//交易所交易员代码
		data.TraderID = getString(e, "TI");
		//本地报单编号
		data.OrderLocalID = getString(e, "OL");
		//结算会员编号
		data.ClearingPartID = getString(e, "CPI");
		//业务单元
		data.BusinessUnit = getString(e, "BU");
		//序号
		data.SequenceNo = getString(e, "XI");
		//交易日
		data.TradingDay = getString(e, "TD");
		//结算编号
		data.SettlementID = getString(e, "SI");
		//经纪公司报单编号
		data.BrokerOrderSeq = getString(e, "BO");
		//成交来源
		data.TradeSource = getString(e, "TS");
		//手续费
		data.Commission = getDouble(e, "C");
		//平仓盈亏
		data.CloseProfit = getDoubleN(e, "CP");
	}
}
ssInitApi();
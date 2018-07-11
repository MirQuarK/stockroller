package com.hzxc.chz.server.utils;
/** 
*
* @author chz
* @version create at：2017年3月28日 下午8:25:05 
*
*/
public class UserAgent {
	private String os;
	private String browser;
	private boolean mobile;
	private boolean mac;
	private boolean iphone;
	private boolean android;
	private boolean wechat;
	private boolean qq;
	private boolean qqb;
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public boolean isMobile() {
		return mobile;
	}
	public void setMobile(boolean mobile) {
		this.mobile = mobile;
	}
	public boolean isMac() {
		return mac;
	}
	public void setMac(boolean mac) {
		this.mac = mac;
	}
	public boolean isIphone() {
		return iphone;
	}
	public void setIphone(boolean iphone) {
		this.iphone = iphone;
	}
	public boolean isAndroid() {
		return android;
	}
	public void setAndroid(boolean android) {
		this.android = android;
	}
	public boolean isWechat() {
		return wechat;
	}
	public void setWechat(boolean wechat) {
		this.wechat = wechat;
	}
	public boolean isQq() {
		return qq;
	}
	public void setQq(boolean qq) {
		this.qq = qq;
	}
	public boolean isQqb() {
		return qqb;
	}
	public void setQqb(boolean qqb) {
		this.qqb = qqb;
	}
	
//	mac: o.indexOf("Mac") > -1,
//	ios: !!o.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
//	android: o.indexOf("Android") > -1 || o.indexOf("Linux") > -1,
//	weixin: !!o.match(/MicroMessenger/i),
//	qq: !!o.match(/QQ/i),
//	qqb : !!o.match("QQB")
}

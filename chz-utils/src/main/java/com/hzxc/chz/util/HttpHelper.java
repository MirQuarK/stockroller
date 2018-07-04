package com.hzxc.chz.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author chz
 * @version create at：2017年3月24日 下午2:38:14
 *
 */
public class HttpHelper {
	public static String getRemoteIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (StringUtils.isBlank(ip)) {
			ip = request.getRemoteHost();
		}
		return ip;
	}

	public static String getUserAgent(HttpServletRequest request){
		return request.getHeader("User-Agent");
	}
	
	public static String getRequestParam(HttpServletRequest request,String paramName,int maxLen){
		String value = request.getParameter(paramName);
		if (value != null && value.length() > maxLen) {
			value = value.substring(0, maxLen);
		}
		return value;
	}

	public static String getRequestURIWithQueryString(HttpServletRequest request){
		return request.getRequestURI()+"?"+request.getQueryString();
	}

}

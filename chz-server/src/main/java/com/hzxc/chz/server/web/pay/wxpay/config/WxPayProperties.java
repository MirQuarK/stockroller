package com.hzxc.chz.server.web.pay.wxpay.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


/**
 * wxpay pay properties
 *
 * @author Binary Wang
 */
@Service
public class WxPayProperties {
  /**
   * 设置微信公众号的appid
   */
  @Value(value = "${wxpay.wxpayappId}")
  private String appId;

  /**
   * 微信支付商户号
   */
  @Value(value = "${wxpay.wxpaymchId}")
  private String mchId;

  /**
   * 微信支付商户密钥
   */
  @Value(value = "${wxpay.wxpaymchKey}")
  private String mchKey;

  /**
   * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
   */
  @Value(value = "${wxpay.wxpaysubAppId}")
  private String subAppId;

  /**
   * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
   */
  @Value(value = "${wxpay.wxpaysubMchId}")
  private String subMchId;

  /**
   * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
   */
  @Value(value = "${wxpay.wxpaykeyPath}")
  private String keyPath;

  public String getAppId() {
    return this.appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getMchId() {
    return mchId;
  }

  public void setMchId(String mchId) {
    this.mchId = mchId;
  }

  public String getMchKey() {
    return mchKey;
  }

  public void setMchKey(String mchKey) {
    this.mchKey = mchKey;
  }

  public String getSubAppId() {
    return subAppId;
  }

  public void setSubAppId(String subAppId) {
    this.subAppId = subAppId;
  }

  public String getSubMchId() {
    return subMchId;
  }

  public void setSubMchId(String subMchId) {
    this.subMchId = subMchId;
  }

  public String getKeyPath() {
    return this.keyPath;
  }

  public void setKeyPath(String keyPath) {
    this.keyPath = keyPath;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this,
        ToStringStyle.MULTI_LINE_STYLE);
  }
}

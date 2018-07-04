package com.hzxc.chz.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create by chz on 2018/3/7
 */
@Component
@ConfigurationProperties(prefix="wechat")
public class WechatConfig {
    private List<Map<String, String>> miniAppList = new ArrayList<>();

    public List<Map<String, String>> getMiniAppList() {
        return miniAppList;
    }

    public void setMiniAppList(List<Map<String, String>> miniAppList) {
        this.miniAppList = miniAppList;
    }
}

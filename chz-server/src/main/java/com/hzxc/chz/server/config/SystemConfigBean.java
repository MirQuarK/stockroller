package com.hzxc.chz.server.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hzxc.chz.dao.GxqSystemConfigRepository;
import com.hzxc.chz.entity.GxqSystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by hdwang on 2017/11/7.
 * 系统配置
 */
@Component
public class SystemConfigBean {
    @Autowired
    GxqSystemConfigRepository configRepository;


    @Value(value = "${custom.rmb-withdraw-options}")
    private String defaultWithdrawOptions;


    private List<JSONObject> recCategory;


    private String searchHotWords;


    @PostConstruct
    @Scheduled(fixedDelay = 300000)
    public void load() {
        List<GxqSystemConfig> list = configRepository.findAll();
        for (GxqSystemConfig systemConfig : list) {
            String key = systemConfig.getConfigKey();
            String value = systemConfig.getConfigValue();
            try {
                switch (key) {
                    case "withdraw.options":
                        break;
                    case "rec.category":
                        // TODO 修改读取位置。从原来的systemconfig表读取变成从ad_video_class表读取，并根据用户channel区分。
                        recCategory = JSON.parseArray(value,JSONObject.class);
                        break;
                    case "search.hotwords":
                        searchHotWords = value;
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public String getSearchHotWords() {
        return searchHotWords;
    }
}

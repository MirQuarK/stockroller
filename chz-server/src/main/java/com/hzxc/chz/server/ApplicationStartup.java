package com.hzxc.chz.server;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import weixin.popular.api.API;

public class ApplicationStartup implements ApplicationContextInitializer {

    @Override
    public void initialize (ConfigurableApplicationContext configurableApplicationContext) {
        API.setApiHandler(new API.APIHandler() {

            @Override
            public String ticket(String appid, String type) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String ticket(String component_appid, String authorizer_appid, String type) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String componentAccessToken(String component_appid) {
                // TODO Auto-generated method stub
                return component_appid;
            }

            @Override
            public String accessToken(String appid) {
                // TODO Auto-generated method stub
                return appid;
            }

            @Override
            public String accessToken(String component_appid, String authorizer_appid) {
                // TODO Auto-generated method stub
                return null;
            }
        });
    }
}
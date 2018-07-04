package com.hzxc.chz.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * create by chz on 2017/12/25
 */
@Configuration
@EnableRedisHttpSession
public class HttpSessionConfig {

    // @Bean
    // public LettuceConnectionFactory connectionFactory() {
    //     return new LettuceConnectionFactory();
    // }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
//        return new HeaderHttpSessionStrategy(); // header认证，x-auth-token
//        return new CookieHttpSessionStrategy(); // cookie认证
        return new MySessionStrategy(); // 自定义的认证策略，结合header和cookie
    }
}
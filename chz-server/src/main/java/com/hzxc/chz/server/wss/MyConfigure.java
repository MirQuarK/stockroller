package com.hzxc.chz.server.wss;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfigure
{

    @Bean
    public MyEndpointConfigure newConfigure()
    {
        return new MyEndpointConfigure();
    }
}
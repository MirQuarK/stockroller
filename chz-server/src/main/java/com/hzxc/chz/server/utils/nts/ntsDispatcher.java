package com.hzxc.chz.server.utils.nts;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 请求分排器
 */
@Component
public class ntsDispatcher implements ApplicationContextAware {
    private ExecutorService executorService = Executors.newFixedThreadPool(1024);
    private ApplicationContext app;

    /**
     * 加载当前application.xml
     *
     * @param ctx
     * @throws BeansException
     */
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.app = ctx;
    }
}
package com.hzxc.chz.server.utils.nts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class boster {
    @Autowired
    ntsListener ntsl;

    @PostConstruct
    public void boost() {
        ntsl.start();
    }
}

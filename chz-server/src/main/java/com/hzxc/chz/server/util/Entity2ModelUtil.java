package com.hzxc.chz.server.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * create by chz on 2017/12/26
 */
@Component
public class Entity2ModelUtil {
    @Value("${domain.avatarUrlHost}")
    private String avatarUrlHost;
    @Value("${domain.imageUrlHost}")
    private String imageUrlHost;
    @Value("${domain.videoUrlHost}")
    private String videoUrlHost;

}
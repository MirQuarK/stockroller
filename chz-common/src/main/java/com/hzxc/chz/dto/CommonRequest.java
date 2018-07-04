package com.hzxc.chz.dto;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 通用响应
 */
public class CommonRequest implements Serializable {
    @Override
    public String toString() {
        return JSON.toJSONString(this,true);
    }
}

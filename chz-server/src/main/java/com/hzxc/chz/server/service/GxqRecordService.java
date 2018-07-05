package com.hzxc.chz.server.service;

import com.hzxc.chz.dao.GxqCurrencyRecordRepository;
import com.hzxc.chz.dao.GxqLoginRecordRepository;

import com.hzxc.chz.entity.GxqLoginRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * create by chz on 2017/12/4
 */
@Service
public class GxqRecordService {
    private static Logger logger = LoggerFactory.getLogger(GxqRecordService.class);
    @Autowired
    GxqLoginRecordRepository loginRecordRepository;

    @Autowired
    GxqCurrencyRecordRepository gxqCurrencyRecordRepository;

    @Async
    public void saveLoginRecord(GxqLoginRecord record) {
        logger.debug("save login record:{}",record);
        loginRecordRepository.save(record);
    }

}

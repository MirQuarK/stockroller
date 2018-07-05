package com.hzxc.chz.server.service.impl;

import com.hzxc.chz.entity.GxqCurrency;
import com.hzxc.chz.server.service.GxqBillService;
import com.hzxc.chz.server.service.GxqCurrencyService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * create by chz on 2017/12/6
 */
@Service
public class GxqCurrencyServiceImpl implements GxqCurrencyService {
    @Override
    public List<GxqCurrency> getByTimePage (int userid, Date start, Date end, int sindex, int count) {
        return null;
    }

    @Override
    public GxqCurrency getById (int id) {
        return null;
    }
}

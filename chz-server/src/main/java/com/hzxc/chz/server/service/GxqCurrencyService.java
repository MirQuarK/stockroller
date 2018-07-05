package com.hzxc.chz.server.service;

import com.hzxc.chz.entity.GxqCurrency;

import java.util.Date;
import java.util.List;

public interface GxqCurrencyService {
    List<GxqCurrency> getByTimePage(int userid, Date start, Date end, int sindex, int count);
    GxqCurrency getById(int id);
}

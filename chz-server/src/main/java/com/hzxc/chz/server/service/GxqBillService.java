package com.hzxc.chz.server.service;

import com.hzxc.chz.entity.GxqBill;

import java.util.Date;
import java.util.List;

public interface GxqBillService {
    List<GxqBill> getByTimePage(int userId, Date start, Date end, int sindex, int count);
    int getCount(int userId, Date start, Date end);
    GxqBill getByUserIdAndId(int userId, int id);
    boolean saveBill(GxqBill gb);
}

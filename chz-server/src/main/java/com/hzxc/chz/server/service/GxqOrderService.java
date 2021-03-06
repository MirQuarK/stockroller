package com.hzxc.chz.server.service;

import com.hzxc.chz.entity.GxqOrder;

import java.util.Date;
import java.util.List;

public interface GxqOrderService {
    List<GxqOrder> getByTimePage(int userId, Date start, Date end, int sindex, int count);
    int getCount(int userId, Date start, Date end);
    GxqOrder getByUserIdAndId(int userId, int id);
    boolean saveOrder(GxqOrder go);
}

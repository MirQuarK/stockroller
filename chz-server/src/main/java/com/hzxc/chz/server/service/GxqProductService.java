package com.hzxc.chz.server.service;

import com.hzxc.chz.entity.GxqProduct;

import java.util.Date;
import java.util.List;

public interface GxqProductService {
    List<GxqProduct> getByTimePage(int userId, Date start, Date end, int sindex, int count);
    GxqProduct getByUserIdAndId(int userId, int id);
}

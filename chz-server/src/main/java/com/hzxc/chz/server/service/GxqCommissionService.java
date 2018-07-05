package com.hzxc.chz.server.service;

import com.hzxc.chz.entity.GxqCommission;

import java.util.Date;
import java.util.List;

public interface GxqCommissionService {
    List<GxqCommission> getByTimePage(int userId, Date start, Date end, int sindex, int count);
    GxqCommission getByUserIdAndId(int userId, int id);
}

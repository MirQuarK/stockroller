package com.hzxc.chz.server.service.impl;

import com.hzxc.chz.dao.GxqCommissionRepository;
import com.hzxc.chz.entity.GxqCommission;
import com.hzxc.chz.server.service.GxqCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * create by chz on 2017/12/6
 */
@Service
public class GxqCommissionServiceImpl implements GxqCommissionService {
    @Autowired
    GxqCommissionRepository gxqCommissionRepository;

    @Override
    public List<GxqCommission> getByTimePage (int userId, Date start, Date end, int sindex, int count) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String a = sdf.format(start);
        String b = sdf.format(end);
        return gxqCommissionRepository.getByTimePage(userId, a, b, sindex, count);
    }

    @Override
    public GxqCommission getByUserIdAndId (int userId, int id) {
        return gxqCommissionRepository.getByUserIdAndId(userId, id);
    }

    @Override
    public int getCount(int userid, Date start, Date end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String a = sdf.format(start);
        String b = sdf.format(end);
        return gxqCommissionRepository.getCount(userid, a, b);
    }

    @Override
    public boolean saveComm (GxqCommission gc) {
        try {
            gxqCommissionRepository.save(gc);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}

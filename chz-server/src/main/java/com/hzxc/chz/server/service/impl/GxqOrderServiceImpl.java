package com.hzxc.chz.server.service.impl;

import com.hzxc.chz.dao.GxqOrderRepository;
import com.hzxc.chz.entity.GxqOrder;
import com.hzxc.chz.server.service.GxqOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * create by chz on 2017/12/6
 */
@Service
public class GxqOrderServiceImpl implements GxqOrderService {

    @Autowired
    GxqOrderRepository gxqOrderRepository;

    @Override
    public List<GxqOrder> getByTimePage (int userId, Date start, Date end, int sindex, int count) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String a = sdf.format(start);
        String b = sdf.format(end);

        return gxqOrderRepository.getByTimePage(userId, a, b, sindex, count);
    }

    @Override
    public GxqOrder getByUserIdAndId (int userId, int id) {
        return gxqOrderRepository.getByUserIdAndId(userId, id);
    }
}

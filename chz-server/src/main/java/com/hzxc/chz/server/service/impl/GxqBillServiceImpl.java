package com.hzxc.chz.server.service.impl;

import com.hzxc.chz.dao.GxqBillRepository;
import com.hzxc.chz.entity.GxqBill;
import com.hzxc.chz.server.service.GxqBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * create by chz on 2017/12/6
 */
@Service
public class GxqBillServiceImpl implements GxqBillService {
    @Autowired
    GxqBillRepository gxqBillRepository;

    @Override
    public List<GxqBill> getByTimePage (int userId, Date start, Date end, int sindex, int count) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String a = sdf.format(start);
        String b = sdf.format(end);
        return gxqBillRepository.getByTimePage(userId, sdf.format(start), sdf.format(end), sindex, count);
    }

    @Override
    public GxqBill getByUserIdAndId (int userId, int id) {
        return gxqBillRepository.getByUserIdAndId(userId, id);
    }
}

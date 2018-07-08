package com.hzxc.chz.server.service.impl;

import com.hzxc.chz.dao.GxqProductRepository;
import com.hzxc.chz.entity.GxqProduct;
import com.hzxc.chz.server.service.GxqProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * create by chz on 2017/12/6
 */
@Service
public class GxqProductServiceImpl implements GxqProductService {
    @Autowired
    GxqProductRepository gxqProductRepository;

    @Override
    public List<GxqProduct> getByTimePage (int userId, Date start, Date end, int sindex, int count) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String a = sdf.format(start);
        String b = sdf.format(end);
        return gxqProductRepository.getByTimePage(userId, a, b, sindex, count);
    }

    @Override
    public GxqProduct getByUserIdAndId (int userId, int id) {
        return gxqProductRepository.getByUserIdAndId(userId, id);
    }
}

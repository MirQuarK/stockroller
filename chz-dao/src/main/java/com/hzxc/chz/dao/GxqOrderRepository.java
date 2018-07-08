package com.hzxc.chz.dao;

import com.hzxc.chz.entity.GxqOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * create by chz on 2017/12/20
 */
public interface GxqOrderRepository extends JpaRepository<GxqOrder, Long> {

    @Query(value = "select * from gxq_order where user_id = ?1 and create_time BETWEEN ?2 and ?3 limit ?4,?5"
            ,nativeQuery = true)
    List<GxqOrder> getByTimePage(int userId, String start, String end, int sindex, int index);

    GxqOrder getByUserIdAndId(int userId, int id);
}

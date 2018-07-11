package com.hzxc.chz.dao;

import com.hzxc.chz.entity.GxqBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * create by chz on 2017/12/20
 */
public interface GxqBillRepository extends JpaRepository<GxqBill, Long> {

    @Query(value = "select * from gxq_bill where user_id = ?1 and create_time BETWEEN ?2 and ?3 limit ?4, ?5",
            nativeQuery = true)
    List<GxqBill> getByTimePage(int userId, String start, String end, int si, int c);

    @Query(value = "select count(*) from gxq_bill where user_id = ?1 and create_time BETWEEN ?2 and ?3",
            nativeQuery = true)
    int getCount(int userId, String start, String end);

    GxqBill getByUserIdAndId(int userId, int id);
}

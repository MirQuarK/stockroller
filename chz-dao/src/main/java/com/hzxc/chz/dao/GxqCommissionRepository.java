package com.hzxc.chz.dao;

import com.hzxc.chz.entity.GxqCommission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * create by chz on 2017/12/20
 */
public interface GxqCommissionRepository extends JpaRepository<GxqCommission, Long> {
    @Query(value = "select * from gxq_commission where user_id = ?1 and create_time BETWEEN ?2 and ?3 limit ?4,?5",nativeQuery = true)
    List<GxqCommission> getByTimePage(int userId, String start, String end, int sindex, int index);

    GxqCommission getByUserIdAndId(int userId, int id);
}

package com.hzxc.chz.dao;

import com.hzxc.chz.entity.GxqProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * create by chz on 2017/12/20
 */
public interface GxqProductRepository extends JpaRepository<GxqProduct, Long> {
    @Query(value = "select * from gxq_product where user_id = ?1 and create_time BETWEEN ?2 and ?3 limit ?4,?5"
            ,nativeQuery = true)
    List<GxqProduct> getByTimePage(int userId, String start, String end, int sindex, int index);

    GxqProduct getByUserIdAndId(int userId, int id);
}

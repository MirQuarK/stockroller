package com.hzxc.chz.dao;

import com.hzxc.chz.entity.GxqCurrency;
import com.hzxc.chz.entity.GxqProduct;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * create by chz on 2017/12/20
 */
public interface GxqCurrencyRepository extends JpaRepository<GxqCurrency, Long> {
//    void initUserCurrency(Long userId);
}

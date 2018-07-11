package com.hzxc.chz.dao;

import com.hzxc.chz.entity.GxqUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * create by chz on 2017/12/20
 */
public interface GxqUserRepository extends JpaRepository<GxqUser, Long> {
    GxqUser findById(int id);
    GxqUser findByMobile(String mobile);

    @Query(value = "select count(*) from gxq_user",
            nativeQuery = true)
    int getCount();

    @Transactional
    @Modifying
    void deleteById(int userId);

    Page<GxqUser> findAll(Pageable pageable);
}

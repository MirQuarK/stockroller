package com.hzxc.chz.dao;

import com.hzxc.chz.entity.GxqUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * create by chz on 2017/12/20
 */
public interface GxqUserRepository extends JpaRepository<GxqUser, Long> {
    GxqUser findById(int id);
    GxqUser findByMobile(String mobile);

    Page<GxqUser> findAll(Specification<GxqUser> spec, Pageable pageable);
}

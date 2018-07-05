package com.hzxc.chz.dao;

import com.hzxc.chz.entity.GxqSystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GxqSystemConfigRepository extends JpaRepository<GxqSystemConfig, Long> {
    GxqSystemConfig findByConfigKey(String configKey);

    @Query(value = "show VARIABLES like 'c%'",nativeQuery = true)
    List<Object> findVar();
}

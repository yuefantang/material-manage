package com.dongyu.company.register.dao;

import com.dongyu.company.register.domain.MiProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * MI登记工序数据处理s
 *
 * @author TYF
 * @date 2018/11/16
 * @since 1.0.0
 */
public interface ProcessDao extends JpaRepository<MiProcess, Long>, JpaSpecificationExecutor<MiProcess> {
}

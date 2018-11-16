package com.dongyu.company.register.dao;

import com.dongyu.company.register.domain.MiRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *MI登记数据处理
 *
 * @author TYF
 * @date 2018/11/15
 * @since 1.0.0
 */
public interface RegisterDao extends JpaRepository<MiRegister, Long>, JpaSpecificationExecutor<MiRegister> {


}

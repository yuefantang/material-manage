package com.dongyu.company.order.dao;

import com.dongyu.company.order.domain.Surplus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 余料处理数据处理
 *
 * @author TYF
 * @date 2018/11/28
 * @since 1.0.0
 */
public interface SurplusDao extends JpaRepository<Surplus, Long>, JpaSpecificationExecutor<Surplus> {
}

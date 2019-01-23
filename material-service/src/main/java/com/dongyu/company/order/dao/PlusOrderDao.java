package com.dongyu.company.order.dao;

import com.dongyu.company.order.domain.PlusOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 补单数据处理
 *
 * @author TYF
 * @date 2019/1/23
 * @since 1.0.0
 */
public interface PlusOrderDao extends JpaRepository<PlusOrder, Long>, JpaSpecificationExecutor<PlusOrder> {
}

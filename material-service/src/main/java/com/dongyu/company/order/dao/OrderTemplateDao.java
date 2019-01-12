package com.dongyu.company.order.dao;

import com.dongyu.company.order.domain.OrderTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 样板数据处理
 *
 * @author TYF
 * @date 2019/1/12
 * @since 1.0.0
 */
public interface OrderTemplateDao extends JpaRepository<OrderTemplate, Long>, JpaSpecificationExecutor<OrderTemplate> {

    //根据DY编号查询
    OrderTemplate findByDyCode(String dyCode);

    //根据DY编号查询未收费开单的样板
    OrderTemplate findByDyCodeAndChargeOpening(String dyCode, Integer chargeOpening);
}

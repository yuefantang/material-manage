package com.dongyu.company.order.dao;

import com.dongyu.company.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 下单数据处理
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
public interface OrderDao extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    //查找最新的一条下单记录
    Order findFirstByOrderByCreateTimeDesc();

}

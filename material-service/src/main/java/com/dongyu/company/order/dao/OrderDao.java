package com.dongyu.company.order.dao;

import com.dongyu.company.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 下单数据处理
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
public interface OrderDao extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    //查询未删除的下单
    Order findByIdAndDeleted(Long id, Integer deleted);

    //查找最新的一条下单记录
    Order findFirstByOrderByCreateTimeDesc();

    //根据投产单号查询下单
    Order findByCommissioningCode(String commissioningCode);

    Order findByCommissioningCodeAndDeleted(String commissioningCode, Integer deleted);

    //根据投产单号查询未收费开单的下单
    Order findByCommissioningCodeAndChargeOpening(String commissioningCode, Integer chargeOpening);

    //根据miRegisterId查找下单
    List<Order> findByMiRegisterIdAndDeleted(Long miRegisterId, Integer deleted);
}

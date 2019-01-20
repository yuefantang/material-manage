package com.dongyu.company.finance.dao;

import com.dongyu.company.finance.domain.Receivable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 收款相关数据处理
 *
 * @author TYF
 * @date 2019/1/20
 * @since 1.0.0
 */
public interface ReceivableDao extends JpaRepository<Receivable, Long>, JpaSpecificationExecutor<Receivable> {

    //根据客户名称和款项年月份去重
    Receivable findByCustomerNameAndFundMonthAndDeleted(String customerName, String fundMonth, Integer deleted);

}

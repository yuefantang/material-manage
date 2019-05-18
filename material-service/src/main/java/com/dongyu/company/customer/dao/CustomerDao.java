package com.dongyu.company.customer.dao;

import com.dongyu.company.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 客户数据处理层
 *
 * @author TYF
 * @date 2019/5/11
 * @since 1.0.0
 */
public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    List<Customer> findByCustomerNameAndCustomerTypeAndDeleted(String customerName, String customerType, Integer deleted);
}

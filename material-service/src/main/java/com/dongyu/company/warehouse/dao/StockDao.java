package com.dongyu.company.warehouse.dao;

import com.dongyu.company.warehouse.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 库存数据处理类
 *
 * @author TYF
 * @date 2019/1/22
 * @since 1.0.0
 */
public interface StockDao extends JpaRepository<Stock, Long>, JpaSpecificationExecutor<Stock> {

    //根据dy编号查询
    Stock findByDyCode(String dyCode);
}

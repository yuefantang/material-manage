package com.dongyu.company.finance.dao;

import com.dongyu.company.finance.domain.MiPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * MI登记价格数据处理
 *
 * @author TYF
 * @date 2018/12/5
 * @since 1.0.0
 */
public interface MiPriceDao extends JpaRepository<MiPrice, Long>, JpaSpecificationExecutor<MiPrice> {

    MiPrice findByMiDyCode(String miDyCode);
}

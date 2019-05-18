package com.dongyu.company.dict.dao;

import com.dongyu.company.dict.domain.StaticData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * 静态数据处理DAO
 *
 * @author TYF
 * @date 2019/5/14
 * @since 1.0.0
 */
public interface StaticDataDao extends JpaRepository<StaticData, Long>, JpaSpecificationExecutor<StaticData> {

}

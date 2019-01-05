package com.dongyu.company.quality.dao;

import com.dongyu.company.quality.domain.Quality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 品质问题数据处理层
 *
 * @author TYF
 * @date 2019/1/5
 * @since 1.0.0
 */
public interface QualityDao extends JpaRepository<Quality, Long>, JpaSpecificationExecutor<Quality> {

}

package com.dongyu.company.register.dao;

import com.dongyu.company.register.domain.MiRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

/**
 * MI登记数据处理
 *
 * @author TYF
 * @date 2018/11/15
 * @since 1.0.0
 */
@Transactional
public interface RegisterDao extends JpaRepository<MiRegister, Long>, JpaSpecificationExecutor<MiRegister> {

    //MI登记时去重
    MiRegister findByMiDyCode(String miDyCode);

    //下单时需要判断是否存在没删除
    MiRegister findByMiDyCodeAndDeleted(String miDyCode, Integer deleted);

    MiRegister findById(Long id);
}

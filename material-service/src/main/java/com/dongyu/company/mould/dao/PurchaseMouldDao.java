package com.dongyu.company.mould.dao;

import com.dongyu.company.mould.domain.PurchaseMould;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 模具采购数据处理层
 *
 * @author TYF
 * @date 2018/11/11
 * @since 1.0.0
 */
public interface PurchaseMouldDao extends JpaRepository<PurchaseMould, Long>, JpaSpecificationExecutor<PurchaseMould> {
    //根据DY编号（唯一）查询模具采购数据
    PurchaseMould findByDyCode(String dyCode);

    //根据id查询模具采购数据
    PurchaseMould findOneById(Long id);

    @Modifying
    @Transactional
    @Query("delete from PurchaseMould where id=:id")
    void deleted(@Param("id") Long id);
}

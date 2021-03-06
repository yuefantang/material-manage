package com.dongyu.company.register.dao;

import com.dongyu.company.register.domain.MiProcess;
import com.dongyu.company.register.domain.MiRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * MI登记工序数据处理s
 *
 * @author TYF
 * @date 2018/11/16
 * @since 1.0.0
 */
public interface ProcessDao extends JpaRepository<MiProcess, Long>, JpaSpecificationExecutor<MiProcess> {

    @Modifying
    @Query("delete from MiProcess m where m.miRegisterId=:miRegisterId")
    void deletedByMiRegisterId(@Param("miRegisterId") Long miRegisterId);

    List<MiProcess> findByMiRegisterId(Long miRegisterId);

}

package com.dongyu.company.deliverynote.dao;

import com.dongyu.company.deliverynote.domain.DeliveryNote;
import com.dongyu.company.register.domain.MiRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 货款单数据处理
 *
 * @author TYF
 * @date 2018/12/4
 * @since 1.0.0
 */
public interface DeliveryNoteDao extends JpaRepository<DeliveryNote, Long>, JpaSpecificationExecutor<DeliveryNote> {

    //查找最新的一条货款单记录
    DeliveryNote findFirstByOrderByCreateTimeDesc();

    @Query(value = "select * FROM t_delivery_note WHERE commissioning_code <>''  ORDER BY create_time DESC LIMIT 1",nativeQuery = true)
    DeliveryNote findNewest();
}

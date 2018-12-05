package com.dongyu.company.deliverynote.dao;

import com.dongyu.company.deliverynote.domain.DeliveryNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 货款单数据处理
 *
 * @author TYF
 * @date 2018/12/4
 * @since 1.0.0
 */
public interface DeliveryNoteDao extends JpaRepository<DeliveryNote, Long>, JpaSpecificationExecutor<DeliveryNote> {
}

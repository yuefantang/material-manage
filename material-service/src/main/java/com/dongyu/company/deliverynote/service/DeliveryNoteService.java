package com.dongyu.company.deliverynote.service;

import com.dongyu.company.deliverynote.dto.AddDeliveryNoteDTO;

/**
 * 货款单Service
 *
 * @author TYF
 * @date 2018/12/4
 * @since 1.0.0
 */
public interface DeliveryNoteService {

    /**
     * 货款单新增
     *
     * @param dto
     */
    void add(AddDeliveryNoteDTO dto);
}

package com.dongyu.company.deliverynote.dto;

import lombok.Data;

/**
 * 编辑货款单DTO
 *
 * @author TYF
 * @date 2018/12/6
 * @since 1.0.0
 */
@Data
public class EditDeliveryDTO extends AddOtherDeliveryNoteDTO {

    //货款单ID
    private Long id;
}

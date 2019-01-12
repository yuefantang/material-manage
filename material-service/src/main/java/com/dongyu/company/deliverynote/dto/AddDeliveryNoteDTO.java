package com.dongyu.company.deliverynote.dto;

import lombok.Data;

/**
 * 新增货款单DTO
 *
 * @author TYF
 * @date 2018/12/4
 * @since 1.0.0
 */
@Data
public class AddDeliveryNoteDTO {

    //送货数量
    private String deliveryNum;

    //送货日期yyyy-MM-dd
    private String deliveryDate;

    //投产单号
    private String commissioningCode;

    //货款开单备注
    private String deliveryRemarks;

    //DY编号
    private String dyCode;
}

package com.dongyu.company.deliverynote.dto;

import lombok.Data;

/**
 * 新增其它收费开单DTO
 *
 * @author TYF
 * @date 2018/12/5
 * @since 1.0.0
 */
@Data
public class AddOtherDeliveryNoteDTO {

    //送货数量
    private String deliveryNum;

    //送货日期yyyy-MM-dd
    private String deliveryDate;

    //投产单号
    private String commissioningCode;

    //货款开单备注
    private String deliveryRemarks;

    //DY编号
    private String miDyCode;

    //客户型号
    private String customerModel;

    //客户名称
    private String customerName;

    //客户订单号
    private String customerOrderCode;

    //送货单号
    private String deliveryCode;

    //单价（单位分）
    private String price;

    //金额(单位分)
    private String amount;

    //单位
    private String deliveryUnit;

}

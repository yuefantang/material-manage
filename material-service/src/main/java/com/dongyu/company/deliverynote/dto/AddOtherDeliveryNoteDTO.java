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

    //领取人
    private String receiver;

    //DY编号
    private String miDyCode;

    //客户型号
    private String customerModel;

    //客户名称
    private String customerName;

    //客户订单号
    private String customerOrderCode;

    //单价（单位分）
    private String price;

    //金额(单位分)
    private String amount;

    //单位
    private String deliveryUnit;

    //下单、模具、测试架或样板数据ID
    private Long otherId;

    //开单收费类型（1:模具收费,2:样板收费,3:其它收费,4:订单收费,5:测试架）
    private Integer chargeType;


}

package com.dongyu.company.order.dto;

import lombok.Data;

/**
 * 新增补单记录DTO
 *
 * @author TYF
 * @date 2019/1/23
 * @since 1.0.0
 */
@Data
public class AddPlusOrderDTO {

    //补单ID
    private Long id;

    //原投产单号
    private String commissioningCode;

    //DY编号
    private String orderDyCode;

    //补单日期yyyy-MM-dd
    private String plusOrderDate;

    //交货日期yyyy-MM-dd
    private String deliveryDate;

    //需补单数量
    private Integer plusOrderNum;

    //实补单数量
    private Integer factPlusOrderNum;

    //补单率
    private String plusOrderRate;

    //经济损失
    private String economicLoss;

    //备注
    private String remark;

    //补单原因
    private String reason;

    //处罚情况
    private String punishSituation;

    //余料处理
    private String surplusTreatment;
}

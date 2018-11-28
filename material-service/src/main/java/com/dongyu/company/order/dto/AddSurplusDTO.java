package com.dongyu.company.order.dto;

import lombok.Data;

/**
 * 余料添加DTO
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
@Data
public class AddSurplusDTO {

    //下单ID
    private Long id;

    //余料处理方法
    private String surplusTreatment;

    //备注
    private String remarks;

    //余料PCS
    private String surplusPcs;

    //余料PNL
    private String surplusPnl;

}

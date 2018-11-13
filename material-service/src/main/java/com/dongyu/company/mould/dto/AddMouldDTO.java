package com.dongyu.company.mould.dto;

import lombok.Data;

import java.util.Date;

/**
 * 新增模具采购DTO
 *
 * @author TYF
 * @date 2018/11/11
 * @since 1.0.0
 */
@Data
public class AddMouldDTO {

    //DY编号
    private String dyCode;

    //产品型号
    private String productModel;

    //长（单位毫米）
    private String length;

    //宽（单位毫米）
    private String wide;

    //采购数量
    private String purchaseQuantity;

    //单价（单位分）
    private String price;

    //金额(单位分)
    private String amount;

    //供应商
    private String supplier;

    //采购日期
    private Date purchaseDate;

    //所属客户
    private String affiliatedCustomer;

    //模具类型
    private String mouldType;

    //一模出几
    private String number;

    //采购种类
    private String purchaseType;

    //连接
    private String connect;

    //备注
    private String remark;

}

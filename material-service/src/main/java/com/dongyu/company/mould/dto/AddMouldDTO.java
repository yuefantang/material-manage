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

    //单价（单位元）
    private String mouldPrice;

    //供应商
    private String supplier;

    //采购日期
    private Date purchaseDate;

    //所属客户
    private String affiliatedCustomer;

    //采购种类
    private Integer purchaseType;

    //是否收费不能为空
    private Integer charge;

    //模具使用状态
    private Integer usageState;

    //备注
    private String remark;

    //长（单位毫米）
    private String length;

    //宽（单位毫米）
    private String wide;

    //模具类型
    private String mouldType;

    //一模出几
    private String number;

    //连接
    private String connect;

    //测试架类型
    private String testRackType;

    // 点数
    private String point;

    //气缸
    private String cylinder;

    //金额(单位分)
    private String mouldAmount;


    //采购数量
    //private String purchaseQuantity;
    //测试架单价（单位元）
    // private String rackPrice;
    //测试架金额(单位分)
    // private String rackAmount;


}

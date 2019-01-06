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

    //测试架单价（单位元）
    private String rackPrice;

    //模具单价（单位元）
    private String mouldPrice;

    //测试架金额(单位分)
    private String rackAmount;

    //模具金额(单位分)
    private String mouldAmount;

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
    private Integer purchaseType;

    //连接
    private String connect;

    //备注
    private String remark;

    //是否收费不能为空
    private Integer charge;

    //模具使用状态
    private Integer usageState;
}

package com.dongyu.company.mould.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 模具采购详情返回DTO
 *
 * @author TYF
 * @date 2018/11/11
 * @since 1.0.0
 */
@Data
@ApiModel("模具采购详情返回DTO")
public class MouldDetailDTO {
    @ApiModelProperty(value = "DY编号")
    private String dyCode;

    @ApiModelProperty(value = "产品型号")
    private String productModel;

    @ApiModelProperty(value = "供应商（下拉列表）")
    private String supplier;

    @ApiModelProperty(value = "采购日期yyyy-MM-dd")
    private String purchaseDate;

    @ApiModelProperty(value = "所属客户")
    private String affiliatedCustomer;

    @ApiModelProperty(value = "类型(1:模具,2:测试架)")
    private Integer purchaseType;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否收费（0：不收费，1：收费）")
    private Integer charge;

    @ApiModelProperty(value = "使用状态（1：新购，2：二次采购,3:报废,4:退回客户）")
    private Integer usageState;

    @ApiModelProperty(value = "单价（单位元）")
    private String mouldPrice;

    @ApiModelProperty(value = "长（单位毫米）")
    private String length;

    @ApiModelProperty(value = "宽（单位毫米）")
    private String wide;

    @ApiModelProperty(value = "模具类型（下拉列表）")
    private String mouldType;

    @ApiModelProperty(value = "一模出几")
    private String number;

    @ApiModelProperty(value = "连接（下拉列表）")
    private String connect;

    @ApiModelProperty(value = "测试架类型")
    private String testRackType;

    @ApiModelProperty(value = "点数")
    private String point;

    @ApiModelProperty(value = "气缸")
    private String cylinder;

    @ApiModelProperty(value = "金额(单位元)")
    private String mouldAmount;
}

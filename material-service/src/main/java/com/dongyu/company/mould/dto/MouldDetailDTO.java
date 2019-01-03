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

    @ApiModelProperty(value = "采购数量")
    private String purchaseQuantity;

    @ApiModelProperty(value = "供应商")
    private String supplier;

    @ApiModelProperty(value = "采购日期")
    private String purchaseDate;

    @ApiModelProperty(value = "所属客户")
    private String affiliatedCustomer;

    @ApiModelProperty(value = "模具类型")
    private String mouldType;

    @ApiModelProperty(value = "长（单位毫米）")
    private String length;

    @ApiModelProperty(value = "宽（单位毫米）")
    private String wide;

    @ApiModelProperty(value = "测试架单价（单位元）")
    private String rackPrice;

    @ApiModelProperty(value = "测试架金额(单位元)")
    private String rackAmount;

    @ApiModelProperty(value = "模具单价（单位元）")
    private String mouldPrice;

    @ApiModelProperty(value = "模具金额(单位元)")
    private String mouldAmount;

    @ApiModelProperty(value = "一模出几")
    private String number;

    @ApiModelProperty(value = "采购种类")
    private Integer purchaseType;

    @ApiModelProperty(value = "连接")
    private String connect;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否收费（0：不收费，1：收费）")
    private Integer charge;
}

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

    @ApiModelProperty(value = "单价（单位分）")
    private String price;

    @ApiModelProperty(value = "金额(单位分)")
    private String amount;

    @ApiModelProperty(value = "一模出几")
    private String number;

    @ApiModelProperty(value = "采购种类")
    private String purchaseType;

    @ApiModelProperty(value = "连接")
    private String connect;

    @ApiModelProperty(value = "备注")
    private String remark;

}

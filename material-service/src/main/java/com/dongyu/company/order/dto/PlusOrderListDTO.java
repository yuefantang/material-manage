package com.dongyu.company.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 补单记录返回DTO
 *
 * @author TYF
 * @date 2019/1/23
 * @since 1.0.0
 */
@Data
@ApiModel("补单记录返回DTO")
public class PlusOrderListDTO {

    @ApiModelProperty(value = "补单id")
    private Long id;

    @ApiModelProperty(value = "补单单号")
    private String plusCommissioningCode;

    @ApiModelProperty(value = "原产单号")
    private String commissioningCode;

    @ApiModelProperty(value = "DY编号")
    private String orderDyCode;

    @ApiModelProperty(value = "补单日期")
    private String plusOrderDate;

    @ApiModelProperty(value = "交货日期")
    private String deliveryDate;

    @ApiModelProperty(value = "平方数")
    private double squareNum;

    @ApiModelProperty(value = "需补单数量")
    private Integer plusOrderNum;

    @ApiModelProperty(value = "实补单数量")
    private Integer factPlusOrderNum;

    @ApiModelProperty(value = "补单率")
    private double plusOrderRate;

    @ApiModelProperty(value = "经济损失")
    private double economicLoss;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "补单原因")
    private String reason;

    @ApiModelProperty(value = "处罚情况")
    private String punishSituation;

    @ApiModelProperty(value = "余料处理")
    private String surplusTreatment;

    @ApiModelProperty(value = "产品型号")
    private String customerModel;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

}

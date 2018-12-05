package com.dongyu.company.order.dto;

import com.dongyu.company.register.dto.RegisterDetailDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 下单详情返回DTO
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
@Data
@ApiModel("下单详情返回DTO")
public class OrderDetailDTO {

    @ApiModelProperty(value = "投产单号")
    private String commissioningCode;

    @ApiModelProperty(value = "DY编号")
    private String orderDyCode;

    @ApiModelProperty(value = "订单数量")
    private String orderNum;

    @ApiModelProperty(value = "客户订单号")
    private String customerOrderCode;

    @ApiModelProperty(value = "下单日期yyyy-MM-dd")
    private String orderDate;

    @ApiModelProperty(value = "交货日期yyyy-MM-dd")
    private String deliveryDate;

    @ApiModelProperty(value = "平方数")
    private String squareNum;

    @ApiModelProperty(value = "备品数")
    private String sparePartsNum;

    @ApiModelProperty(value = "备品率")
    private String sparePartsRate;

    @ApiModelProperty(value = "投产数量")
    private String commissioningNum;

    @ApiModelProperty(value = "余料处理方法")
    private String surplusTreatment;

    @ApiModelProperty(value = "余料处理备注")
    private String surplusRemarks;

    @ApiModelProperty(value = "线路印次")
    private String lineImpression;

    @ApiModelProperty(value = "定位孔数")
    private String locatingNum;

    @ApiModelProperty(value = "绿油印次")
    private String greenOilImpression;

    @ApiModelProperty(value = "文字印次")
    private String wordsImpression;

    @ApiModelProperty(value = "碳桥印次")
    private String carbonBridgeImpression;

    @ApiModelProperty(value = "其它印次")
    private String otherImpression;

    @ApiModelProperty(value = "冲床冲次")
    private String punch;

    @ApiModelProperty(value = "MI登记详情")
    private RegisterDetailDTO registerDetailDTO;

    @ApiModelProperty(value = "已完成数量")
    private String completedNum;

    @ApiModelProperty(value = "未完成数量")
    private String uncompletedNum;
}

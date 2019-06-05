package com.dongyu.company.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 样板返回DTO
 *
 * @author TYF
 * @date 2019/1/12
 * @since 1.0.0
 */
@Data
@ApiModel("样板返回DTO")
public class OrderTemplateDTO {

    @ApiModelProperty(value = "样板编号")
    private String templateCode;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户型号")
    private String customerModel;

    @ApiModelProperty(value = "类型")
    private String templateType;

    @ApiModelProperty(value = "板材")
    private String board;

    @ApiModelProperty(value = "板厚")
    private String plateThick;

    @ApiModelProperty(value = "铜厚")
    private String copperThick;

    @ApiModelProperty(value = "数量")
    private String templateNum;

    @ApiModelProperty(value = "面积")
    private String areaNum;

    @ApiModelProperty(value = "长")
    private String templateLength;

    @ApiModelProperty(value = "宽")
    private String templateWide;

    @ApiModelProperty(value = "下单日期")
    private String orderDate;

    @ApiModelProperty(value = "备注")
    private String templateRemark;

    @ApiModelProperty(value = "领取人")
    private String receiver;

    @ApiModelProperty(value = "出货日期")
    private String templateDeliveryDate;

    @ApiModelProperty(value = "是否收费（0：不收费，1：收费）")
    private String charge;
}

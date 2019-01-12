package com.dongyu.company.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty(value = "DY编号")
    private String dyCode;

//    @ApiModelProperty(value = "样板编号")
//    private String templateCode;

    @ApiModelProperty(value = "数量")
    private Integer templateNum;

    @ApiModelProperty(value = "面积")
    private double areaNum;

    @ApiModelProperty(value = "长")
    private double templateLength;

    @ApiModelProperty(value = "宽")
    private double templateWide;

    @ApiModelProperty(value = "厚度")
    private double thickness;

    @ApiModelProperty(value = "类型")
    private String templateType;

    @ApiModelProperty(value = "备注")
    private String templateRemark;

    @ApiModelProperty(value = "领取人")
    private String receiver;

    @ApiModelProperty(value = "板材")
    private String board;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户型号")
    private String customerModel;

    @ApiModelProperty(value = "出货日期")
    private String templateDeliveryDate;

    @ApiModelProperty(value = "收费开单状态（0：未收费开单，1：已收费开单）")
    private Integer chargeOpening;
}

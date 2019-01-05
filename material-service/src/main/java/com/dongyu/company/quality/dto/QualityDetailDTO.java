package com.dongyu.company.quality.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 详情返回DTO
 *
 * @author TYF
 * @date 2019/1/5
 * @since 1.0.0
 */
public class QualityDetailDTO {

    @ApiModelProperty(value = "DY编号")
    private String dyCode;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "产品型号")
    private String customerModel;

    @ApiModelProperty(value = "报废数")
    private Integer scrapNum;

    @ApiModelProperty(value = "订单数")
    private Integer orderNum;

    @ApiModelProperty(value = "报废率")
    private String scrapRate;

    @ApiModelProperty(value = "经济损失")
    private String economicLoss;

    @ApiModelProperty(value = "问题出现时间")
    private String problemTime;

    @ApiModelProperty(value = "报废原因")
    private String scrapReason;
}

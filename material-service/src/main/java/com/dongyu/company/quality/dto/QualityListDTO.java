package com.dongyu.company.quality.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询返回DTO
 *
 * @author TYF
 * @date 2019/1/5
 * @since 1.0.0
 */
@Data
@ApiModel("分页查询返回DTO")
public class QualityListDTO {

    @ApiModelProperty(value = "ID")
    private Long id;

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

    @ApiModelProperty(value = "是否删除（0：未删除，1：已删除）")
    private Integer deleted;
}

package com.dongyu.company.finance.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 账单明细统计返回DTO
 *
 * @author TYF
 * @date 2019/1/21
 * @since 1.0.0
 */
@Data
@ApiModel("账单明细统计返回DTO")
public class BillStatisticsDTO {

    @ApiModelProperty(value = "总金额")
    private double totalAmount;

    @ApiModelProperty(value = "已核实金额")
    private double verifyAmount;

    @ApiModelProperty(value = "未核实金额")
    private double unverifiedAmount;

}

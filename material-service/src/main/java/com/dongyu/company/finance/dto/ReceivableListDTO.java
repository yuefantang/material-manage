package com.dongyu.company.finance.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 收款分页查询返回DTO
 *
 * @author TYF
 * @date 2019/1/20
 * @since 1.0.0
 */
@Data
@ApiModel("收款分页查询返回DTO")
public class ReceivableListDTO {

    @ApiModelProperty(value = "收款ID")
    private Long id;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "款项年月份")
    private String fundMonth;

    @ApiModelProperty(value = "进款金额")
    private Double receivableAmount;

    @ApiModelProperty(value = "收款日期")
    private String receivableDate;

    @ApiModelProperty(value = "备注")
    private String remark;
}

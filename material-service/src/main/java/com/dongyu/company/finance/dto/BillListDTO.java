package com.dongyu.company.finance.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 账单明细返回DTO
 *
 * @author TYF
 * @date 2019/1/21
 * @since 1.0.0
 */
@Data
@ApiModel("账单明细返回DTO")
public class BillListDTO {

    @ApiModelProperty(value = "货款单ID")
    private Long id;

    @ApiModelProperty(value = "送货数量")
    private String deliveryNum;

    @ApiModelProperty(value = "送货日期yyyy-MM-dd")
    private String deliveryDate;

    @ApiModelProperty(value = "DY编号")
    private String miDyCode;

    @ApiModelProperty(value = "客户型号")
    private String customerModel;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户订单号")
    private String customerOrderCode;

    @ApiModelProperty(value = "送货单号")
    private String deliveryCode;

    @ApiModelProperty(value = "单价（单位分）")
    private String price;

    @ApiModelProperty(value = "金额(单位分)")
    private String amount;

    @ApiModelProperty(value = "单位")
    private String deliveryUnit;

    @ApiModelProperty(value = "对账月份")
    private String billMonth;

    @ApiModelProperty(value = "核实状态（0：未核实，1：已核实）")
    private String verifyState;

}

package com.dongyu.company.deliverynote.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 货款单分页查询返回DTO
 *
 * @author TYF
 * @date 2018/12/5
 * @since 1.0.0
 */
@Data
@ApiModel("货款单分页查询返回DTO")
public class DeliveryListDTO {

    @ApiModelProperty(value = "货款单ID")
    private Long id;

    @ApiModelProperty(value = "送货数量")
    private String deliveryNum;

    @ApiModelProperty(value = "送货日期yyyy-MM-dd")
    private String deliveryDate;

    @ApiModelProperty(value = "投产单号")
    private String commissioningCode;

    @ApiModelProperty(value = "货款开单备注")
    private String deliveryRemarks;

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

    @ApiModelProperty(value = "收费种类")
    private String chargeType;

    @ApiModelProperty(value = "领取人")
    private String receiver;

    @ApiModelProperty(value = "送货单是否删除（0：否，1：是）")
    private Integer deleted;
}

package com.dongyu.company.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 下单分页查询返回DTO
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
@Data
@ApiModel("下单分页查询返回DTO")
public class OrderListDTO {

    @ApiModelProperty(value = "下单ID")
    private Long id;

    @ApiModelProperty(value = "客户型号")
    private String customerModel;

    @ApiModelProperty(value = "投产单号")
    private String commissioningCode;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "DY编号")
    private String orderDyCode;

    @ApiModelProperty(value = "订单数量")
    private String orderNum;

    @ApiModelProperty(value = "客户订单号")
    private String customerOrderCode;

    @ApiModelProperty(value = "已完成数量")
    private String completedNum;

    @ApiModelProperty(value = "未完成数量")
    private String uncompletedNum;

    @ApiModelProperty(value = "下单日期yyyy-MM-dd")
    private String orderDate;

    @ApiModelProperty(value = "交货日期yyyy-MM-dd")
    private String deliveryDate;

    @ApiModelProperty(value = "平方数")
    private String squareNum;

    @ApiModelProperty(value = "余料处理数据")
    private AddOrderResultDTO dto;

    @ApiModelProperty(value = "下单是否删除（0：否，1：是）")
    private Integer deleted;
}

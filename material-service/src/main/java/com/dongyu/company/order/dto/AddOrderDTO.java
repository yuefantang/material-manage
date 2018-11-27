package com.dongyu.company.order.dto;

import lombok.Data;

/**
 * 新增下单DTO
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
@Data
public class AddOrderDTO {

    //DY编号
    private String orderDyCode;

    //订单数量
    private String orderNum;

    //客户订单号
    private String ustomercOrderCode;

    //下单日期yyyy-MM-dd
    private String orderDate;

    //交货日期yyyy-MM-dd
    private String deliveryDate;

    //备品率
    private String sparePartsRate;

}

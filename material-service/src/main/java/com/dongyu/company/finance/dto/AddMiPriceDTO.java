package com.dongyu.company.finance.dto;

import lombok.Data;


/**
 * 新增MI登记价格DTO
 *
 * @author TYF
 * @date 2018/12/5
 * @since 1.0.0
 */
@Data
public class AddMiPriceDTO {
    //DY编号
    private String miDyCode;

    //单价（单位分）
    private String price;

    //单价类型
    private String priceType;

    //报价日期yyyy-MM-dd
    private String quotationDate;

    //是否开票(0：否，1：是)
    private String isTicket;

    //付款方式
    private String payType;

    //备注
    private String remark;


}

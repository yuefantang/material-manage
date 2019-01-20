package com.dongyu.company.finance.dto;

import lombok.Data;

import java.util.Date;

/**
 * 新增收款DTO
 *
 * @author TYF
 * @date 2019/1/20
 * @since 1.0.0
 */
@Data
public class AddReceivableDTO {

    //客户名称
    private String customerName;

    //款项年月份
    private String fundMonth;

    //进款金额
    private Double receivableAmount;

    //收款日期
    private Date receivableDate;

    //备注
    private String remark;
}

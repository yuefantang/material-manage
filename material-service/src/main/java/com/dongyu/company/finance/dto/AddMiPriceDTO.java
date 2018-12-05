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
}

package com.dongyu.company.quality.dto;

import lombok.Data;

import java.util.Date;

/**
 * 新增品质问题DTO
 *
 * @author TYF
 * @date 2019/1/5
 * @since 1.0.0
 */
@Data
public class AddQualityDTO {

    //DY编号
    private String dyCode;

    //客户名称
    private String customerName;

    //产品型号
    private String customerModel;

    //报废数
    private Integer scrapNum;

    //订单数
    private Integer orderNum;

    //报废率
    private String scrapRate;

    //经济损失
    private String economicLoss;

    //问题出现时间
    private Date problemTime;

    //报废原因
    private String scrapReason;
}

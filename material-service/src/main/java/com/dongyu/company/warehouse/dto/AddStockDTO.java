package com.dongyu.company.warehouse.dto;

import lombok.Data;

/**
 * 库存登记DTO
 *
 * @author TYF
 * @date 2019/1/22
 * @since 1.0.0
 */
@Data
public class AddStockDTO {

    //库存id
    private Long id;

    //DY编号
    private String dyCode;

    //客户型号
    private String customerModel;

    //客户名称
    private String customerName;

    //库存数量
    private Integer stockNum;

    //备注
    private String remark;
}

package com.dongyu.company.customer.dto;

import lombok.Data;

/**
 * 客户新增DTO
 *
 * @author TYF
 * @date 2019/5/11
 * @since 1.0.0
 */
@Data
public class AddCustomerDTO {

    //客户名称
    private String customerName;

    //客户类型
    private String customerType;

    //客户联系方式
    private String customerContact;

    //客户地址
    private String customerAddress;

}

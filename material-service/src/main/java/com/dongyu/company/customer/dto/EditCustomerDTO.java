package com.dongyu.company.customer.dto;

import lombok.Data;

/**
 * 客户编辑DTO
 *
 * @author TYF
 * @date 2019/5/11
 * @since 1.0.0
 */
@Data
public class EditCustomerDTO extends AddCustomerDTO {

    //客户ID
    private Long id;
}

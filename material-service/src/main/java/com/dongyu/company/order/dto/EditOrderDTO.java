package com.dongyu.company.order.dto;

import lombok.Data;

/**
 * 编辑下单DTO
 *
 * @author TYF
 * @date 2018/11/28
 * @since 1.0.0
 */
@Data
public class EditOrderDTO extends AddOrderDTO {

    //下单ID
    private Long id;
}

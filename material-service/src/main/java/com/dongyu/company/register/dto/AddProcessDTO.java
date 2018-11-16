package com.dongyu.company.register.dto;

import lombok.Data;

/**
 * MI工序新增DTO
 *
 * @author TYF
 * @date 2018/11/16
 * @since 1.0.0
 */
@Data
public class AddProcessDTO {

    //序号
    private String orderNumber;

    //工序
    private String process;

    //备注
    private String remark;
}

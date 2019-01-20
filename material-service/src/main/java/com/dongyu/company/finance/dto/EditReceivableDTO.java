package com.dongyu.company.finance.dto;

import lombok.Data;

/**
 * 编辑收款
 *
 * @author TYF
 * @date 2019/1/20
 * @since 1.0.0
 */
@Data
public class EditReceivableDTO extends AddReceivableDTO {

    //收款id
    private Long id;
}

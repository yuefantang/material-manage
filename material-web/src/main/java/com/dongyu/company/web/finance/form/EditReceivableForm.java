package com.dongyu.company.web.finance.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 编辑收款Form
 *
 * @author TYF
 * @date 2019/1/20
 * @since 1.0.0
 */
@Data
@ApiModel("编辑收款Form")
public class EditReceivableForm extends AddReceivableForm {

    @ApiModelProperty("收款ID")
    private Long id;
}

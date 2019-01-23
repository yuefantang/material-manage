package com.dongyu.company.web.order.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 编辑补单Form
 *
 * @author TYF
 * @date 2019/1/23
 * @since 1.0.0
 */
@Data
@ApiModel("编辑补单Form")
public class EditPlusOrderForm extends AddPlusOrderForm {

    @ApiModelProperty(value = "补单ID")
    private Long id;
}

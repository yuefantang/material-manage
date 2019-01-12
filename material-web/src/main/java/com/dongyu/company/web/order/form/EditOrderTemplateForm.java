package com.dongyu.company.web.order.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 编辑样板Form
 *
 * @author TYF
 * @date 2019/1/12
 * @since 1.0.0
 */
@Data
@ApiModel("编辑样板Form")
public class EditOrderTemplateForm extends AddOrderTemplateForm {

    @ApiModelProperty(value = "样板ID")
    private Long id;
}

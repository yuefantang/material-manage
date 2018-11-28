package com.dongyu.company.web.order.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 编辑下单Form
 *
 * @author TYF
 * @date 2018/11/28
 * @since 1.0.0
 */
@Data
@ApiModel("编辑下单Form")
public class EditOrderForm extends AddOrderForm {

    @ApiModelProperty(value = "下单ID")
    private Long id;
}

package com.dongyu.company.web.customer.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 编辑客户Form
 *
 * @author TYF
 * @date 2019/5/11
 * @since 1.0.0
 */

@Data
@ApiModel("编辑客户Form")
public class EditCustomerForm extends AddCustomerForm {

    @ApiModelProperty(value = "客户ID")
    private Long id;
}

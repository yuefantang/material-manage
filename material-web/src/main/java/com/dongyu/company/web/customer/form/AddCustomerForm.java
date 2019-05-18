package com.dongyu.company.web.customer.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 新增客户Form
 *
 * @author TYF
 * @date 2019/5/11
 * @since 1.0.0
 */
@Data
@ApiModel("新增客户Form")
public class AddCustomerForm {

    @ApiModelProperty(value = "客户名称")
    @NotBlank(message = "客户名称不能为空")
    private String customerName;

    @ApiModelProperty(value = "客户类型(supplier:供应商，customer：客户)传英文")
    @NotBlank(message = "客户类型不能为空")
    private String customerType;

    @ApiModelProperty(value = "客户联系方式")
    private String customerContact;

    @ApiModelProperty(value = "客户地址")
    private String customerAddress;

}

package com.dongyu.company.web.customer.form;

import com.dongyu.company.common.form.PageForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户分页查询Form
 *
 * @author TYF
 * @date 2019/5/11
 * @since 1.0.0
 */
@Data
@ApiModel("客户分页查询Form")
public class CustomerQueryForm extends PageForm {

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户类型(supplier:供应商，customer：客户)传英文")
    private String customerType;

    @ApiModelProperty(value = "客户是否删除（0：未删除，1：已删除）")
    private Integer deleted;

}

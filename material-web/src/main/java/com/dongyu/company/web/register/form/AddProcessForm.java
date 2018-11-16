package com.dongyu.company.web.register.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * MI工序新增Form
 *
 * @author TYF
 * @date 2018/11/16
 * @since 1.0.0
 */
@Data
@ApiModel("MI工序新增Form")
public class AddProcessForm {

    @ApiModelProperty(value = "序号")
    @NotNull(message = "序号不能为空")
    private String orderNumber;

    @ApiModelProperty(value = "工序")
    @NotNull(message = "工序不能为空")
    private String process;

    @ApiModelProperty(value = "备注")
    private String remark;

}

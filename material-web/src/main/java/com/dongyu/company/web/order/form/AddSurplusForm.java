package com.dongyu.company.web.order.form;

import com.dongyu.company.common.constants.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 余料添加Form
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
@Data
@ApiModel("余料添加Form")
public class AddSurplusForm {

    @ApiModelProperty(value = "下单ID")
    private Long orderId;

    @ApiModelProperty(value = "余料处理方法")
    @NotBlank(message = "余料处理方法不能为空")
    private String surplusTreatment;

    @ApiModelProperty(value = "余料处理备注")
    private String surplusRemarks;

    @ApiModelProperty(value = "余料PCS")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "余料PCS格式错误，只能输入数字")
    private String surplusPcs;

    @ApiModelProperty(value = "余料PNL")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "余料PNL格式错误，只能输入数字")
    private String surplusPnl;
}

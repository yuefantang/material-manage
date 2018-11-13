package com.dongyu.company.web.account.form;

import com.dongyu.company.common.constants.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 密码修改Form
 *
 * @author TYF
 * @date 2018/11/12
 * @since 1.0.0
 */
@Data
@ApiModel("密码修改Form")
public class EditPasswordForm {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "第一次输入密码(6位数数字或字母)")
    @NotNull(message = "密码不能为空")
    @Pattern(regexp = Constants.PASSWORD_PATTERN, message = "密码格式有误")
    private String firstPassword;

    @ApiModelProperty(value = "密码确认(6位数数字或字母)")
    @NotNull(message = "密码不能为空")
    @Pattern(regexp = Constants.PASSWORD_PATTERN, message = "密码格式有误")
    private String password;


}

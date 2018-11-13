package com.dongyu.company.web.account.form;

import com.dongyu.company.common.constants.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 用户登陆form
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
@Data
@ApiModel("用户信息")
public class UserForm {

    @ApiModelProperty(value = "用户姓名")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "密码(6位数数字或字母)")
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = Constants.PASSWORD_PATTERN, message = "密码格式有误")
    private String password;
}

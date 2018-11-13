package com.dongyu.company.web.account.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
@Data
@ApiModel("新增用户")
public class AddUserForm extends UserForm{

    @ApiModelProperty(value = "角色id")
    @NotNull(message = "角色不能为空")
    private Long roleId;

}

package com.dongyu.company.web.account.form;

import com.dongyu.company.common.form.PageForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户分页查询
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
@Data
@ApiModel("用户分页查询")
public class UserQueryForm extends PageForm {

    @ApiModelProperty(value = "用户账号(支持模糊查询)")
    private String userName;
}

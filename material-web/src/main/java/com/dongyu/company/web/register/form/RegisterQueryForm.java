package com.dongyu.company.web.register.form;

import com.dongyu.company.common.form.PageForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Mi登记分页查询Form
 *
 * @author TYF
 * @date 2018/11/17
 * @since 1.0.0
 */
@Data
@ApiModel("Mi登记分页查询Form")
public class RegisterQueryForm extends PageForm {

    @ApiModelProperty(value = "DY编号")
    private String miDyCode;

}

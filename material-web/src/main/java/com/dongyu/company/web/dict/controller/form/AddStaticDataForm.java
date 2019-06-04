package com.dongyu.company.web.dict.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 新增列表数据Form
 *
 * @author TYF
 * @date 2019/6/4
 * @since 1.0.0
 */

@Data
@ApiModel("新增列表数据Form")
public class AddStaticDataForm {

    @ApiModelProperty(value = "数据类型")
    @NotBlank(message = "数据类型不能为空")
    private String codeType;

    @ApiModelProperty(value = "数据key")
    @NotBlank(message = "数据key不能为空")
    private String codeKey;

    @ApiModelProperty(value = "数据key对应的值")
    @NotBlank(message = "数据key对应的值不能为空")
    private String codeValue;

    @ApiModelProperty(value = "数据描述")
    @NotBlank(message = "数据描述不能为空")
    private String codeDesc;
}

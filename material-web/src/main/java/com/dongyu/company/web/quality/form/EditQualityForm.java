package com.dongyu.company.web.quality.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 编辑品质问题Form
 *
 * @author TYF
 * @date 2019/1/6
 * @since 1.0.0
 */
@Data
@ApiModel("编辑品质问题Form")
public class EditQualityForm extends AddQualityForm {

    @ApiModelProperty(value = "品质问题ID")
    private Long id;

}

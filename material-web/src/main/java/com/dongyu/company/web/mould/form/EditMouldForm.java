package com.dongyu.company.web.mould.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模具采购编辑form
 *
 * @author TYF
 * @date 2018/11/11
 * @since 1.0.0
 */
@Data
@ApiModel("模具采购编辑form")
public class EditMouldForm extends AddMouldForm {

    @ApiModelProperty(value = "模具采购ID")
    private Long id;
}

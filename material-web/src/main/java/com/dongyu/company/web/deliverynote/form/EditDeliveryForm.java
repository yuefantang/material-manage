package com.dongyu.company.web.deliverynote.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 编辑货款单Form
 *
 * @author TYF
 * @date 2018/12/6
 * @since 1.0.0
 */
@Data
@ApiModel("编辑货款单Form")
public class EditDeliveryForm extends AddOtherDeliveryNoteForm {

    @ApiModelProperty(value = "货款单ID")
    private Long id;

}

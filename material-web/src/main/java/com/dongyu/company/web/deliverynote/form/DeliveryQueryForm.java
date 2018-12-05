package com.dongyu.company.web.deliverynote.form;

import com.dongyu.company.common.form.PageForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 货款单分页查询Form
 *
 * @author TYF
 * @date 2018/12/4
 * @since 1.0.0
 */
@Data
@ApiModel("货款单分页查询Form")
public class DeliveryQueryForm extends PageForm {

    @ApiModelProperty(value = "送货单号")
    private String deliveryCode;
}

package com.dongyu.company.web.order.form;

import com.dongyu.company.common.form.PageForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 补单分页查询Form
 *
 * @author TYF
 * @date 2019/1/23
 * @since 1.0.0
 */

@Data
@ApiModel("补单分页查询Form")
public class PlusOrderQueryForm extends PageForm {

    @ApiModelProperty(value = "DY编号")
    private String orderDyCode;

    @ApiModelProperty(value = "补单单号")
    private String plusCommissioningCode;

    @ApiModelProperty(value = "订单是否删除（0：未删除，1：已删除），默认0")
    private Integer deleted;
}

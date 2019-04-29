package com.dongyu.company.web.order.form;

import com.dongyu.company.common.form.PageForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 下单分页查询Form
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
@Data
@ApiModel("下单分页查询Form")
public class OrderQueryForm extends PageForm {

    @ApiModelProperty(value = "投产单号")
    private String commissioningCode;

    @ApiModelProperty(value = "DY编号")
    private String orderDyCode;

    @ApiModelProperty(value = "是否完成状态（0：未完成，1：完成）")
    private Integer completeState;

    @ApiModelProperty(value = "订单是否删除（0：未删除，1：已删除），默认不传")
    private Integer deleted;

    @ApiModelProperty("收费开单（0：未收费开单，1：已收费开单）默认0")
    private Integer chargeOpening;
}

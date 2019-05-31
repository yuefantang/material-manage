package com.dongyu.company.web.deliverynote.form;

import com.dongyu.company.common.form.PageForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

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

    @ApiModelProperty(value = "送货单是否作废(0：否，1：是)，默认0")
    private Integer deleted;

    @ApiModelProperty(value = "DY编号")
    private String miDyCode;

    @ApiModelProperty(value = "送货日期开始时间")
    private String deliveryDateStart;

    @ApiModelProperty(value = "送货日期结束时间")
    private String deliveryDateEnd;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户型号")
    private String customerModel;
}

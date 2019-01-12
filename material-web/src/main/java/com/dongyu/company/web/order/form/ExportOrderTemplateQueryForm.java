package com.dongyu.company.web.order.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 样板导出查询Form
 *
 * @author TYF
 * @date 2019/1/12
 * @since 1.0.0
 */
@Data
@ApiModel("样板导出查询Form")
public class ExportOrderTemplateQueryForm {

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "DY编号")
    private String dyCode;

    @ApiModelProperty(value = "订单是否删除（0：未删除，1：已删除）")
    private Integer deleted;

    @ApiModelProperty(value = "收费开单（0：未收费开单，1：已收费开单）默认0")
    private Integer chargeOpening;
}

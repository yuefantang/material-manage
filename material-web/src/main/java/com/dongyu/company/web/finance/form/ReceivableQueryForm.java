package com.dongyu.company.web.finance.form;

import com.dongyu.company.common.form.PageForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 收款分页查询Form
 *
 * @author TYF
 * @date 2019/1/20
 * @since 1.0.0
 */

@Data
@ApiModel("收款分页查询Form")
public class ReceivableQueryForm extends PageForm {

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "款项年月份")
    private String fundMonth;

    @ApiModelProperty(value = "订单是否删除（0：未删除，1：已删除），默认0")
    private Integer deleted;
}

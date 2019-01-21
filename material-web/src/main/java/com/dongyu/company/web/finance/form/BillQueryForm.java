package com.dongyu.company.web.finance.form;

import com.dongyu.company.common.form.PageForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 账单明细分页查询Form
 *
 * @author TYF
 * @date 2019/1/21
 * @since 1.0.0
 */
@Data
@ApiModel("账单明细分页查询Form")
public class BillQueryForm extends PageForm {

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "对账月份")
    private String billMonth;

    @ApiModelProperty(value = "核实状态（0：未核实，1：已核实），默认-1查全部")
    private Integer verifyState;


}

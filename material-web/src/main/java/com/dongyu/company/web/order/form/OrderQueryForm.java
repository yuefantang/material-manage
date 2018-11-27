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

}

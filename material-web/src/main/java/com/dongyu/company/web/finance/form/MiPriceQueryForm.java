package com.dongyu.company.web.finance.form;

import com.dongyu.company.common.form.PageForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * MI登记价格分页查询Form
 *
 * @author TYF
 * @date 2018/12/5
 * @since 1.0.0
 */
@Data
@ApiModel("MI登记价格分页查询Form")
public class MiPriceQueryForm extends PageForm {

    @ApiModelProperty(value = "DY编号")
    @NotBlank(message = "DY编号不能为空")
    private String miDyCode;

    @ApiModelProperty(value = "订单是否删除（0：未删除，1：已删除），默认0")
    private Integer deleted;
}

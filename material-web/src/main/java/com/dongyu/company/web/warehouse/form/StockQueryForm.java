package com.dongyu.company.web.warehouse.form;

import com.dongyu.company.common.form.PageForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 库存分页查询Form
 *
 * @author TYF
 * @date 2019/1/22
 * @since 1.0.0
 */
@Data
@ApiModel("库存分页查询Form")
public class StockQueryForm extends PageForm {

    @ApiModelProperty(value = "DY编号")
    private String dyCode;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "Mi登记是否删除（0：未删除，1：已删除），默认0")
    private Integer deleted;
}

package com.dongyu.company.web.warehouse.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 库存编辑Form
 *
 * @author TYF
 * @date 2019/1/22
 * @since 1.0.0
 */
@Data
@ApiModel("库存编辑Form")
public class EditStockForm extends AddStockForm {

    @ApiModelProperty(value = "库存id")
    private Long id;
}

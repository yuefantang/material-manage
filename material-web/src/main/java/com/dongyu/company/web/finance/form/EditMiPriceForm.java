package com.dongyu.company.web.finance.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 编辑MI登记价格Form
 *
 * @author TYF
 * @date 2018/12/6
 * @since 1.0.0
 */
@Data
@ApiModel("编辑MI登记价格Form")
public class EditMiPriceForm extends AddMiPriceForm {

    @ApiModelProperty("MI登记价格ID")
    private Long id;
}

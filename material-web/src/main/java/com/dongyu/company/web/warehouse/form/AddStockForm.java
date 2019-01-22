package com.dongyu.company.web.warehouse.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 库存登记Form
 *
 * @author TYF
 * @date 2019/1/22
 * @since 1.0.0
 */
@Data
@ApiModel("库存登记Form")
public class AddStockForm {

    @ApiModelProperty(value = "DY编号")
    @NotNull(message = "DY编号不能为空")
    private String dyCode;

    @ApiModelProperty(value = "客户型号")
    private String customerModel;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "库存数量")
    private Integer stockNum;

    @ApiModelProperty(value = "备注")
    private String remark;

}

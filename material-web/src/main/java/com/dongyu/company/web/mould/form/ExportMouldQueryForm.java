package com.dongyu.company.web.mould.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模具采购导出查询Form
 *
 * @author TYF
 * @date 2018/11/13
 * @since 1.0.0
 */
@Data
@ApiModel("模具采购分页查询Form")
public class ExportMouldQueryForm {
    @ApiModelProperty("DY编号(支持模糊查询)")
    private String dyCode;

    @ApiModelProperty("产品型号(支持模糊查询)")
    private String productModel;

    @ApiModelProperty("供应商(支持模糊查询)")
    private String supplier;

    @ApiModelProperty("采购日期开始(yyyy-MM-dd)")
    private String purchaseDateStart;

    @ApiModelProperty("采购日期结束(yyyy-MM-dd)")
    private String purchaseDateEnd;

    @ApiModelProperty("所属客户(支持模糊查询)")
    private String affiliatedCustomer;
}

package com.dongyu.company.web.mould.form;

import com.dongyu.company.common.form.PageForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模具采购分页查询Form
 *
 * @author TYF
 * @date 2018/11/11
 * @since 1.0.0
 */
@Data
@ApiModel("模具采购分页查询Form")
public class MouldQueryForm extends PageForm {

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

    @ApiModelProperty(value = "模具采购是否删除（0：未删除，1：已删除），默认0")
    private Integer deleted;

    @ApiModelProperty("是否收费（0：不收费，1：收费），默认0")
    private Integer charge;

    @ApiModelProperty("收费开单（0：未收费开单，1：已收费开单）默认0")
    private Integer chargeOpening;

}

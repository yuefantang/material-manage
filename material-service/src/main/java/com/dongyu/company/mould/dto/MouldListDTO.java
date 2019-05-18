package com.dongyu.company.mould.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模具采购查询返回DTO
 *
 * @author TYF
 * @date 2018/11/11
 * @since 1.0.0
 */
@Data
@ApiModel("模具采购查询返回DTO")
public class MouldListDTO {

    @ApiModelProperty(value = "模具采购ID")
    private Long id;

    @ApiModelProperty(value = "DY编号")
    private String dyCode;

    @ApiModelProperty(value = "类型(1:模具,2:测试架)")
    private Integer purchaseType;

    @ApiModelProperty(value = "产品型号")
    private String productModel;

//    @ApiModelProperty(value = "采购数量")
//    private String purchaseQuantity;

    @ApiModelProperty(value = "供应商")
    private String supplier;

    @ApiModelProperty(value = "采购日期")
    private String purchaseDate;

    @ApiModelProperty(value = "所属客户")
    private String affiliatedCustomer;

//    @ApiModelProperty(value = "模具类型")
//    private String mouldType;

    @ApiModelProperty(value = "是否收费（0：不收费，1：收费）")
    private Integer charge;

    @ApiModelProperty(value = "使用状态（1：新购，2：二次采购,3:报废,4:退回客户）")
    private Integer usageState;


    @ApiModelProperty(value = "是否删除（0：未删除，1：已删除）")
    private Integer deleted;

}

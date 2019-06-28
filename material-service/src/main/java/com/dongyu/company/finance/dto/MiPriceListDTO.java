package com.dongyu.company.finance.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * MI登记价格分页查询返回DTO
 *
 * @author TYF
 * @date 2018/12/5
 * @since 1.0.0
 */
@Data
@ApiModel("MI登记价格分页查询返回DTO")
public class MiPriceListDTO {

    @ApiModelProperty(value = "MI登记价格ID")
    private Long miPriceId;

    @ApiModelProperty(value = "DY编号")
    private String miDyCode;

    @ApiModelProperty(value = "单价（单位分）")
    private String price;

    @ApiModelProperty(value = "客户型号")
    private String customerModel;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户料号")
    private String customerMaterialNum;

    @ApiModelProperty(value = "板材类型")
    private String plateType;

    @ApiModelProperty(value = "板厚")
    private String plateThick;

    @ApiModelProperty(value = "板材商")
    private String plateMerchant;

    @ApiModelProperty(value = "工艺")
    private String technology;

    @ApiModelProperty(value = "报价日期yyyy-MM-dd")
    private String quotationDate;

    @ApiModelProperty("铜厚")
    private String copperThick;

    @ApiModelProperty(value = "MI登记价格是否删除（0：否，1：是）")
    private Integer deleted;
}

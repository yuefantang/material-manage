package com.dongyu.company.finance.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * MI登记价格详情DTO
 *
 * @author TYF
 * @date 2018/12/6
 * @since 1.0.0
 */
@Data
@ApiModel("MI登记价格详情DTO")
public class MiPriceDetailDTO {

    @ApiModelProperty(value = "DY编号")
    private String miDyCode;

    @ApiModelProperty(value = "单价（单位分）")
    private String price;

    @ApiModelProperty(value = "单价类型")
    private String priceType;

    @ApiModelProperty(value = "报价日期yyyy-MM-dd")
    private String quotationDate;

    @ApiModelProperty(value = "是否开票(0：否，1：是)")
    private String isTicket;

    @ApiModelProperty(value = "付款方式")
    private String payType;

    @ApiModelProperty(value = "备注")
    private String remark;

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

    @ApiModelProperty("铜厚")
    private String copperThick;
}

package com.dongyu.company.register.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Mi登记分页查询返回DTO
 *
 * @author TYF
 * @date 2018/11/17
 * @since 1.0.0
 */
@Data
@ApiModel("Mi登记分页查询返回DTO")
public class RegisterListDTO {

    @ApiModelProperty(value = "Mi登记ID")
    private Long id;

    @ApiModelProperty(value = "DY编号")
    private String miDyCode;

    @ApiModelProperty(value = "客户型号")
    private String customerModel;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户料号")
    private String customerMaterialNum;

    @ApiModelProperty(value = "板材类型")
    private String plateType;

    @ApiModelProperty(value = "板材商")
    private String plateMerchant;

    @ApiModelProperty(value = "模具编号")
    private String moldNumber;

    @ApiModelProperty(value = "开模商")
    private String openMoldMerchant;

    @ApiModelProperty(value = "开模日期yyyy-MM-dd")
    private String openMoldDate;

    @ApiModelProperty(value = "样板确认日期yyyy-MM-dd")
    private String confirmDate;

    @ApiModelProperty(value = "建档日期yyyy-MM-dd")
    private String recordDate;


}

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

}

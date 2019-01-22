package com.dongyu.company.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 库存返回DTO
 *
 * @author TYF
 * @date 2019/1/22
 * @since 1.0.0
 */

@Data
@ApiModel("库存返回DTO")
public class StockListDTO {

    @ApiModelProperty(value = "库存id")
    private Long id;

    @ApiModelProperty(value = "DY编号")
    private String dyCode;

    @ApiModelProperty(value = "客户型号")
    private String customerModel;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "库存数量")
    private Integer stockNum;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "库存登记时间")
    private String createTime;
}

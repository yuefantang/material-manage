package com.dongyu.company.customer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户返回DTO
 *
 * @author TYF
 * @date 2019/5/11
 * @since 1.0.0
 */
@Data
@ApiModel("客户返回DTO")
public class CustomerListDTO {

    @ApiModelProperty(value = "客户ID")
    private Long id;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户类型")
    private String customerType;

    @ApiModelProperty(value = "客户联系方式")
    private String customerContact;

    @ApiModelProperty(value = "客户地址")
    private String customerAddress;

    @ApiModelProperty(value = "模具是否删除（0：未删除，1：已删除）")
    private Integer deleted;
}

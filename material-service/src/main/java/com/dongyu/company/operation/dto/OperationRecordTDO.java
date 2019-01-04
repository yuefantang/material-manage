package com.dongyu.company.operation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 操作记录日志返回DTO
 *
 * @author TYF
 * @date 2019/1/4
 * @since 1.0.0
 */
@Data
@ApiModel("操作记录日志返回DTO")
public class OperationRecordTDO {

    @ApiModelProperty(value = "修改之前的值")
    private String oldValue;

    @ApiModelProperty(value = "修改之后的值")
    private String newValue;

    @ApiModelProperty(value = "修改的字段名称")
    private String attribute;

    @ApiModelProperty(value = "操作类型")
    private String operationType;

    @ApiModelProperty(value = "操作人名称")
    private String operationName;
}

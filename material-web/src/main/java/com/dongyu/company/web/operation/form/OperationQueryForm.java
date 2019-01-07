package com.dongyu.company.web.operation.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 日志查询Form
 *
 * @author TYF
 * @date 2019/1/7
 * @since 1.0.0
 */
@Data
@ApiModel("日志查询Form")
public class OperationQueryForm {

    @ApiModelProperty(value = "所在模块名称")
    private String entity;

    @ApiModelProperty(value = "详情页数据id")
    private Long entityId;

}

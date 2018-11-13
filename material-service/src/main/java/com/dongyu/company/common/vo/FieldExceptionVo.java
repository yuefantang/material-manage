package com.dongyu.company.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 字段异常信息
 *
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
@Setter
@Getter
@ApiModel(description = "字段异常信息")
public class FieldExceptionVo {

    @ApiModelProperty("字段名")
    private String fieldName;

    @ApiModelProperty("该字段出错信息")
    private String msg;

    public FieldExceptionVo(String fieldName, String msg) {
        this.fieldName = fieldName;
        this.msg = msg;
    }

    public FieldExceptionVo() {
    }
}

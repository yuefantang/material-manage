package com.dongyu.company.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 异常VO
 *
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
@Setter
@Getter
@ApiModel(description = "异常数据")
public class ExceptionVo {

    @ApiModelProperty(value = "异常Code，可选", example = "null")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;

    @ApiModelProperty(value = "异常消息，可选", example = "参数不符合要求")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

}

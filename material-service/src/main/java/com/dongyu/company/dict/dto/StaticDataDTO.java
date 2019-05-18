package com.dongyu.company.dict.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 下拉列表静态数据值返回集合DTO
 *
 * @author TYF
 * @date 2019/5/16
 * @since 1.0.0
 */
@Data
@ApiModel("下拉列表静态数据值返回集合DTO")
public class StaticDataDTO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "数据类型")
    private String codeType;

    @ApiModelProperty(value = "数据key")
    private String codeKey;

    @ApiModelProperty(value = "数据key对应的值")
    private String codeValue;

    @ApiModelProperty(value = "数据描述")
    private String codeDesc;

    @ApiModelProperty(value = "排序号")
    private Long sortId;

    @ApiModelProperty(value = "状态")
    private Integer deleted;
}

package com.dongyu.company.web.dict.controller.form;

import com.dongyu.company.common.form.PageForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 列表数据分页查询Form
 *
 * @author TYF
 * @date 2019/6/4
 * @since 1.0.0
 */
@Data
@ApiModel("列表数据分页查询Form")
public class StaticQueryForm extends PageForm {

    @ApiModelProperty(value = "数据描述")
    private String codeDesc;

    @ApiModelProperty(value = "数据类型")
    private String codeType;

    @ApiModelProperty(value = "数据key")
    private String codeKey;

    @ApiModelProperty(value = "数据key对应的值")
    private String codeValue;

    @ApiModelProperty(value = "状态（0：未删除，1：已删除）")
    private Integer deleted;
}

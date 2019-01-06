package com.dongyu.company.web.quality.form;

import com.dongyu.company.common.form.PageForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询条件Form
 *
 * @author TYF
 * @date 2019/1/6
 * @since 1.0.0
 */
@Data
@ApiModel("分页查询条件Form")
public class QualityQueryForm extends PageForm {

    @ApiModelProperty("DY编号(支持模糊查询)")
    private String dyCode;

    @ApiModelProperty(value = "是否删除（0：未删除，1：已删除），默认0")
    private Integer deleted;

}

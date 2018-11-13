package com.dongyu.company.common.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页表单
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
@Data
@ApiModel("分页表单")
public class PageForm {

    @ApiModelProperty("当前页数")
    private Integer pageNo;

    @ApiModelProperty("每页条数")
    private Integer pageSize;
}

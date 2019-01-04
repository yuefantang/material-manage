package com.dongyu.company.web.register.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Mi登记导出查询Form
 *
 * @author TYF
 * @date 2019/1/4
 * @since 1.0.0
 */
@Data
@ApiModel("Mi登记导出查询Form")
public class ExportRegisterQueryForm {

    @ApiModelProperty(value = "DY编号")
    private String miDyCode;
}

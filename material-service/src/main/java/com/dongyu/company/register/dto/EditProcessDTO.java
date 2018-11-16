package com.dongyu.company.register.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 编辑和详情工序DTO
 *
 * @author TYF
 * @date 2018/11/16
 * @since 1.0.0
 */
@Data
@ApiModel("编辑和详情工序DTO")
public class EditProcessDTO extends AddProcessDTO {

    @ApiModelProperty(name = "MI登记下工序ID")
    private Long id;
}

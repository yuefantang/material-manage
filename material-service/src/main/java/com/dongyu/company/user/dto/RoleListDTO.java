package com.dongyu.company.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色下拉列表返回DTO
 *
 * @author TYF
 * @date 2018/11/12
 * @since 1.0.0
 */
@Data
@ApiModel("角色下拉列表返回DTO")
public class RoleListDTO {

    @ApiModelProperty(value = "角色ID")
    private Long id;

    @ApiModelProperty(value = "角色描述")
    private String descript;


}

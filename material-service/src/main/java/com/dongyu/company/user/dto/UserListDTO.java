package com.dongyu.company.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;

/**
 * 返回用户集合DTO
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
@Data
@ApiModel("返回用户集合DTO")
public class UserListDTO {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "用户账号")
    private String userName;

    @ApiModelProperty(value = "用户角色名称")
    private String roleName;

    @ApiModelProperty(value = "用户角色名称")
    private String descript;

}

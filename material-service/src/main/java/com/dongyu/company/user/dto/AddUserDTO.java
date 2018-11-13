package com.dongyu.company.user.dto;

import lombok.Data;

/**
 * 新增用户DTO
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
@Data
public class AddUserDTO extends UserDTO {

    //角色id
    private Long roleId;
}

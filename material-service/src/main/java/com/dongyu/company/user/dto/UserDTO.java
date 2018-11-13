package com.dongyu.company.user.dto;

import lombok.Data;

/**
 * 用户登陆DTO
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
@Data
public class UserDTO {
    //用户名
    private String userName;

    //密码
    private String password;
}

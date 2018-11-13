package com.dongyu.company.user.dto;

import lombok.Data;

/**
 * 密码修改DTO
 *
 * @author TYF
 * @date 2018/11/12
 * @since 1.0.0
 */
@Data
public class EditPasswordDTO {
    //用户ID
    private Long id;

    //第一次输入密码
    private String firstPassword;

    //再次确认密码
    private String password;

}

package com.dongyu.company.user.dto;

import com.dongyu.company.common.dto.PageQueryDTO;
import lombok.Data;

/**
 * 用户分页查询DTO
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
@Data
public class UserQueryDTO extends PageQueryDTO {

    //用户账号
    private String userName;
}

package com.dongyu.company.register.dto;

import com.dongyu.company.common.dto.PageQueryDTO;
import lombok.Data;

/**
 * Mi登记分页查询DTO
 *
 * @author TYF
 * @date 2018/11/17
 * @since 1.0.0
 */
@Data
public class RegisterQueryDTO extends PageQueryDTO {

    //DY编号
    private String miDyCode;
}

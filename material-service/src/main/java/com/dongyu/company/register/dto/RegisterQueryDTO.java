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

    //Mi登记是否删除（0：未删除，1：已删除），默认0
    private Integer deleted;

    //客户型号
    private String customerModel;

    //客户名称
    private String customerName;
}

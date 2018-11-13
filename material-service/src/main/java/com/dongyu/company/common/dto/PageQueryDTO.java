package com.dongyu.company.common.dto;

import lombok.Data;

/**
 * 分页DTO
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
@Data
public class PageQueryDTO {

    //当前页数
    private Integer pageNo;

    //每页条数
    private Integer pageSize;
}

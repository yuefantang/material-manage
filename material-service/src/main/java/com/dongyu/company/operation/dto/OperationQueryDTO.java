package com.dongyu.company.operation.dto;

import lombok.Data;

/**
 * 日志查询DTO
 *
 * @author TYF
 * @date 2019/1/7
 * @since 1.0.0
 */
@Data
public class OperationQueryDTO {


    //所在模块名称
    private String entity;

    //详情页数据id
    private Long entityId;
}

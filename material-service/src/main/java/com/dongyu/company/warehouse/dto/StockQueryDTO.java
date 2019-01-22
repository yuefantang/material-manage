package com.dongyu.company.warehouse.dto;

import com.dongyu.company.common.dto.PageQueryDTO;
import lombok.Data;

/**
 * 库存分页查询DTO
 *
 * @author TYF
 * @date 2019/1/22
 * @since 1.0.0
 */
@Data
public class StockQueryDTO extends PageQueryDTO {

    //DY编号
    private String dyCode;

    //客户名称
    private String customerName;

    //Mi登记是否删除（0：未删除，1：已删除），默认0
    private Integer deleted;
}

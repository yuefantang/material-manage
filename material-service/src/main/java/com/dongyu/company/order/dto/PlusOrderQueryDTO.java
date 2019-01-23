package com.dongyu.company.order.dto;

import com.dongyu.company.common.dto.PageQueryDTO;
import lombok.Data;

/**
 * 补单分页查询DTO
 *
 * @author TYF
 * @date 2019/1/23
 * @since 1.0.0
 */
@Data
public class PlusOrderQueryDTO extends PageQueryDTO {

    //DY编号
    private String orderDyCode;

    //补单单号
    private String plusCommissioningCode;

    //订单是否删除（0：未删除，1：已删除），默认0
    private Integer deleted;
}

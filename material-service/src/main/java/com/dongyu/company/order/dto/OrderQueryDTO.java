package com.dongyu.company.order.dto;

import com.dongyu.company.common.dto.PageQueryDTO;
import lombok.Data;

/**
 * 下单分页查询DTO
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
@Data
public class OrderQueryDTO extends PageQueryDTO {

    // 投产单号
    private String commissioningCode;

    //是否完成状态（0：未完成，1：完成）
    private Integer completeState;
}

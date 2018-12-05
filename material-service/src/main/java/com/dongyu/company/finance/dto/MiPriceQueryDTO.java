package com.dongyu.company.finance.dto;

import com.dongyu.company.common.dto.PageQueryDTO;
import lombok.Data;

/**
 * MI登记价格分页查询DTO
 *
 * @author TYF
 * @date 2018/12/5
 * @since 1.0.0
 */
@Data
public class MiPriceQueryDTO extends PageQueryDTO {

    //DY编号
    private String miDyCode;

    //订单是否删除（0：未删除，1：已删除），默认0
    private Integer deleted;
}

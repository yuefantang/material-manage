package com.dongyu.company.order.dto;

import com.dongyu.company.common.dto.PageQueryDTO;
import lombok.Data;

/**
 * 样板分页查询DTO
 *
 * @author TYF
 * @date 2019/1/12
 * @since 1.0.0
 */
@Data
public class OrderTemplateQueryDTO extends PageQueryDTO {

    //客户名称
    private String customerName;

    //DY编号
    private String dyCode;

    //订单是否删除（0：未删除，1：已删除）
    private Integer deleted;

    //收费开单（0：未收费开单，1：已收费开单）默认0
    private Integer chargeOpening;
}

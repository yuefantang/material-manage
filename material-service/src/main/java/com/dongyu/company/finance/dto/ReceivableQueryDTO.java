package com.dongyu.company.finance.dto;

import com.dongyu.company.common.dto.PageQueryDTO;
import lombok.Data;

/**
 * 收款查询DTO
 *
 * @author TYF
 * @date 2019/1/20
 * @since 1.0.0
 */
@Data
public class ReceivableQueryDTO extends PageQueryDTO {

    //客户名称
    private String customerName;

    //款项年月份
    private String fundMonth;

    //订单是否删除（0：未删除，1：已删除），默认0
    private Integer deleted;
}

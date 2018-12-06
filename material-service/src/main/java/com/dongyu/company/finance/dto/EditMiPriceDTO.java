package com.dongyu.company.finance.dto;

import lombok.Data;

/**
 * 编辑MI登记价格DTO
 *
 * @author TYF
 * @date 2018/12/6
 * @since 1.0.0
 */
@Data
public class EditMiPriceDTO extends AddMiPriceDTO {

    //MI登记价格ID
    private Long id;
}

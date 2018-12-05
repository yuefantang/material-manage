package com.dongyu.company.deliverynote.dto;

import com.dongyu.company.common.dto.PageQueryDTO;
import lombok.Data;

/**
 * 货款单分页查询DTO
 *
 * @author TYF
 * @date 2018/12/4
 * @since 1.0.0
 */
@Data
public class DeliveryQueryDTO extends PageQueryDTO {

    //送货单号
    private String deliveryCode;

}

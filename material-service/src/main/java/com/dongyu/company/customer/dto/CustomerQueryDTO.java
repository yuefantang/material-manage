package com.dongyu.company.customer.dto;

import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.dto.PageQueryDTO;
import lombok.Data;

/**
 * 客户分页查询DTO
 *
 * @author TYF
 * @date 2019/5/11
 * @since 1.0.0
 */
@Data
public class CustomerQueryDTO extends PageQueryDTO {

    //客户名称
    private String customerName;

    //客户类型
    private String customerType;

    //客户是否删除（0：未删除，1：已删除）
    private Integer deleted = DeletedEnum.UNDELETED.getValue();
}

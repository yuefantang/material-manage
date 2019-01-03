package com.dongyu.company.dict.dto;

import lombok.Data;

import java.util.List;

/**
 * 字典返回集合DTO
 *
 * @author TYF
 * @date 2019/1/2
 * @since 1.0.0
 */
@Data
public class DictResultDTO {

    //采购种类
    private List<DictDTO> procurementTypeEnum;

    //模具使用状态
    private List<DictDTO> usageStateEnum;
}

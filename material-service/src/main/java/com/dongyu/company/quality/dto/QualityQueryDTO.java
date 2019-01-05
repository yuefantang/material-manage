package com.dongyu.company.quality.dto;

import com.dongyu.company.common.dto.PageQueryDTO;
import lombok.Data;

/**
 * 分页查询条件DTO
 *
 * @author TYF
 * @date 2019/1/5
 * @since 1.0.0
 */
@Data
public class QualityQueryDTO extends PageQueryDTO {

    //DY编号
    private String dyCode;

    //是否删除（0：未删除，1：已删除），默认0
    private Integer deleted;
}

package com.dongyu.company.dict.dto;

import com.dongyu.company.common.dto.PageQueryDTO;
import lombok.Data;

/**
 * 列表数据分页查询DTO
 *
 * @author TYF
 * @date 2019/6/4
 * @since 1.0.0
 */
@Data
public class StaticQueryDTO extends PageQueryDTO {

    //数据描述
    private String codeDesc;

    //数据类型
    private String codeType;

    //数据key
    private String codeKey;

    //数据key对应的值
    private String codeValue;

    //状态（0：未删除，1：已删除）
    private Integer deleted;

}

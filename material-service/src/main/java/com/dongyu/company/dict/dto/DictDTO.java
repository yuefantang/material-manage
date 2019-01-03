package com.dongyu.company.dict.dto;

import lombok.Data;

/**
 * 字典返回dto
 *
 * @author TYF
 * @date 2019/1/2
 * @since 1.0.0
 */
@Data
public class DictDTO {
    /**
     * 字典key
     */
    private Integer key;

    /**
     * 字典value
     */
    private String value;
}

package com.dongyu.company.quality.dto;

import lombok.Data;

/**
 * 品质问题编辑DTO
 *
 * @author TYF
 * @date 2019/1/6
 * @since 1.0.0
 */
@Data
public class EditQualityDTO extends AddQualityDTO {

    //品质问题ID
    private Long id;
}

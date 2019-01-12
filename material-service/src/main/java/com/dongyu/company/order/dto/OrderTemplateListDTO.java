package com.dongyu.company.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 样板返回DTO
 *
 * @author TYF
 * @date 2019/1/12
 * @since 1.0.0
 */

@Data
@ApiModel("样板返回DTO")
public class OrderTemplateListDTO extends OrderTemplateDTO {

    @ApiModelProperty(value = "样板ID")
    private Long id;
}

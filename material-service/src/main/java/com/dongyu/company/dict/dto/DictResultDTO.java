package com.dongyu.company.dict.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("下拉列表字典值返回集合DTO")
public class DictResultDTO {

    @ApiModelProperty(value = "采购种类")
    private List<DictDTO> procurementTypeEnum;

    @ApiModelProperty(value = "模具使用状态")
    private List<DictDTO> usageStateEnum;

    @ApiModelProperty(value = "其它收费开单的类型")
    private List<DictDTO> chargeTypeEnum;
}

package com.dongyu.company.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 下单新增返回DTO
 *
 * @author TYF
 * @date 2018/11/28
 * @since 1.0.0
 */
@Data
@ApiModel("下单新增返回DTO")
public class AddOrderResultDTO {

    @ApiModelProperty(value = "下单ID")
    private Long id;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件图片表ID")
    private Long commonFileId;

    @ApiModelProperty(value = "共用料张数")
    private String haredMaterialsNum;

    @ApiModelProperty(value = "余下张数")
    private String remainNum;

    @ApiModelProperty(value = "大料PCS数")
    private String pcsNumber;
}

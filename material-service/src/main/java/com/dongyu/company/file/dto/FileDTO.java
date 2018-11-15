package com.dongyu.company.file.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文件图片返回DTO
 *
 * @author TYF
 * @date 2018/11/15
 * @since 1.0.0
 */
@Data
@ApiModel("文件图片返回DTO")
public class FileDTO {

    @ApiModelProperty(value = "文件图片存储ID")
    private Long id;

    @ApiModelProperty(value = "文件存储路径")
    private String filePath;

    @ApiModelProperty(value = "文件名")
    private String fileName;

}

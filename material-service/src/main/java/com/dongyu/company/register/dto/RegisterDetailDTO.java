package com.dongyu.company.register.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Mi登记详情返回DTO
 *
 * @author TYF
 * @date 2018/11/16
 * @since 1.0.0
 */
@Data
@ApiModel("Mi登记详情返回DTO")
public class RegisterDetailDTO {

    @ApiModelProperty(value = "Mi登记ID")
    private Long id;

    @ApiModelProperty(value = "DY编号")
    private String miDyCode;

    @ApiModelProperty(value = "客户型号")
    private String customerModel;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户料号")
    private String customerMaterialNum;

    @ApiModelProperty(value = "板材类型")
    private String plateType;

    @ApiModelProperty(value = "板厚")
    private String plateThick;

    @ApiModelProperty(value = "铜厚")
    private String copperThick;

    @ApiModelProperty(value = "板材商")
    private String plateMerchant;

    @ApiModelProperty(value = "工艺")
    private String technology;

    @ApiModelProperty(value = "工程更改内容")
    private String changeContent;

    @ApiModelProperty(value = "单片尺寸长")
    private String singleSizeLength;

    @ApiModelProperty(value = "单片尺寸宽")
    private String singleSizeWide;

    @ApiModelProperty(value = "模片尺寸长")
    private String dieSizeLength;

    @ApiModelProperty(value = "模片尺寸宽")
    private String dieSizeWide;

    @ApiModelProperty(value = "一模出几")
    private String miNumber;

    @ApiModelProperty(value = "模具编号")
    private String moldNumber;

    @ApiModelProperty(value = "模具类型")
    private String mouldType;

    @ApiModelProperty(value = "连接")
    private String miConnect;

    @ApiModelProperty(value = "开模商")
    private String openMoldMerchant;

    @ApiModelProperty(value = "开模日期yyyy-MM-dd")
    private String openMoldDate;

    @ApiModelProperty(value = "菲林编号")
    private String filmNumber;

    @ApiModelProperty(value = "线路")
    private String line;

    @ApiModelProperty(value = "文字")
    private String words;

    @ApiModelProperty(value = "其它")
    private String other;

    @ApiModelProperty(value = "周期标记")
    private String cycleMarker;

    @ApiModelProperty(value = "UL标记")
    private String ulMarker;

    @ApiModelProperty(value = "ROHS标记")
    private String rohsMarker;

    @ApiModelProperty(value = "绿油")
    private String greenOil;

    @ApiModelProperty(value = "碳桥")
    private String carbonBridge;

    @ApiModelProperty(value = "标记位置")
    private String markPosition;

    @ApiModelProperty(value = "样板确认日期yyyy-MM-dd")
    private String confirmDate;

    @ApiModelProperty(value = "建档日期yyyy-MM-dd")
    private String recordDate;

    @ApiModelProperty(value = "大料尺寸")
    private String sheetSize;

    @ApiModelProperty(value = "更改日期yyyy-MM-dd")
    private String changeDate;

    @ApiModelProperty(value = "更改依据")
    private String changeBasis;

    @ApiModelProperty(value = "大料PCS数")
    private String pcsNumber;

    @ApiModelProperty(value = "大料利用率")
    private String utilizationRatio;

    @ApiModelProperty(value = "A(尺寸)")
    private String miA;

    @ApiModelProperty(value = "B(尺寸)")
    private String miB;

    @ApiModelProperty(value = "C(尺寸)")
    private String miC;

    @ApiModelProperty(value = "D(尺寸)")
    private String miD;

    @ApiModelProperty(value = "大料冲次")
    private String punching;

    @ApiModelProperty(value = "大料PNL数")
    private String pnlNumber;

    @ApiModelProperty(value = "注意事项")
    private String attention;

    @ApiModelProperty(value = "是否允许投产")
    private String production;

    @ApiModelProperty(value = "文件图片表ID")
    private Long commonFileId;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "MI工序详情返回集合")
    private List<EditProcessDTO> processDTOS;
}

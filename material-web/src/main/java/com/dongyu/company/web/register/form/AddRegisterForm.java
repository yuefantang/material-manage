package com.dongyu.company.web.register.form;

import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.register.dto.AddProcessDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Mi登记新增Form
 *
 * @author TYF
 * @date 2018/11/16
 * @since 1.0.0
 */
@Data
@ApiModel("Mi登记新增Form")
public class AddRegisterForm {

    @ApiModelProperty(value = "DY编号")
    @NotNull(message = "DY编号不能为空")
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

    @ApiModelProperty(value = "单片尺寸")
    private String singleSize;

    @ApiModelProperty(value = "模片尺寸")
    private String dieSize;

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
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "开模日期不能为空")
    private String openMoldDate;

    @ApiModelProperty(value = "菲林编号")
    private String filmNumber;

    @ApiModelProperty(value = "线路")
    private boolean isLine = false;

    @ApiModelProperty(value = "文字")
    private boolean isWords = false;

    @ApiModelProperty(value = "其它")
    private boolean isOther = false;

    @ApiModelProperty(value = "周期标记")
    private boolean cycleMarker = false;

    @ApiModelProperty(value = "UL标记")
    private boolean ulMarker = false;

    @ApiModelProperty(value = "ROHS标记")
    private boolean rohsMarker = false;

    @ApiModelProperty(value = "绿油")
    private boolean isGreenOil = false;

    @ApiModelProperty(value = "碳桥")
    private boolean isCarbonBridge = false;

    @ApiModelProperty(value = "标记位置")
    private String markPosition;

    @ApiModelProperty(value = "样板确认日期yyyy-MM-dd")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "样板确认日期不能为空")
    private String ConfirmDate;

    @ApiModelProperty(value = "建档日期yyyy-MM-dd")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "建档日期不能为空")
    private String recordDate;

    @ApiModelProperty(value = "大料尺寸")
    private String sheetSize;

    @ApiModelProperty(value = "更改日期yyyy-MM-dd")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "更改日期不能为空")
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
    private boolean isProduction = false;

    @ApiModelProperty(value = "文件图片表ID")
    private Long commonFileId;

    @ApiModelProperty(value = "MI工序集合")
    private List<AddProcessDTO> processDTOS;
}

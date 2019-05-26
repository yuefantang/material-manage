package com.dongyu.company.web.register.form;

import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.register.dto.AddProcessDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @NotNull(message = "客户型号不能为空")
    private String customerModel;

    @ApiModelProperty(value = "客户名称")
    @NotNull(message = "客户名称不能为空")
    private String customerName;

    @ApiModelProperty(value = "客户料号")
    private String customerMaterialNum;

    @ApiModelProperty(value = "板材类型")
    @NotNull(message = "板材类型不能为空")
    private String plateType;

    @ApiModelProperty(value = "板厚")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "板厚格式错误，只能输入数字")
    @NotNull(message = "板厚不能为空")
    private String plateThick;

    @ApiModelProperty(value = "铜厚")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "铜厚格式错误，只能输入数字")
    @NotNull(message = "铜厚不能为空")
    private String copperThick;

    @ApiModelProperty(value = "板材商")
    @NotNull(message = "板材商不能为空")
    private String plateMerchant;

    @ApiModelProperty(value = "工艺")
    @NotNull(message = "工艺不能为空")
    private String technology;

    @ApiModelProperty(value = "工程更改内容")
    private String changeContent;

    @ApiModelProperty(value = "单片尺寸长")
    @NotNull(message = "单片尺寸长不能为空")
    private Double singleSizeLength;

    @ApiModelProperty(value = "单片尺寸宽")
    @NotNull(message = "单片尺寸宽不能为空")
    private Double singleSizeWide;

    @ApiModelProperty(value = "模片尺寸长")
    @NotNull(message = "模片尺寸长不能为空")
    private Double dieSizeLength;

    @ApiModelProperty(value = "模片尺寸宽")
    @NotNull(message = "模片尺寸宽不能为空")
    private Double dieSizeWide;

    @ApiModelProperty(value = "一模出几")
    @NotNull(message = "一模出几不能为空")
    @Pattern(regexp = Constants.POSITIVE_NUMBER_PATTERN, message = "一模出几格式错误，只能输入正整数")
    private String miNumber;

    @ApiModelProperty(value = "模具编号")
    @NotNull(message = "模具编号不能为空")
    private String moldNumber;

    @ApiModelProperty(value = "模具类型")
    private String mouldType;

    @ApiModelProperty(value = "连接")
    @NotNull(message = "连接不能为空")
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
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "线路格式错误，只能输入数字")
    @NotNull(message = "线路不能为空")
    private String line;

    @ApiModelProperty(value = "文字")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "文字格式错误，只能输入数字")
    @NotNull(message = "文字不能为空")
    private String words;

    @ApiModelProperty(value = "其它")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "其它格式错误，只能输入数字")
    @NotNull(message = "其它不能为空")
    private String other;

    @ApiModelProperty(value = "周期标记")
    @NotNull(message = "周期标记不能为空")
    private String cycleMarker;

    @ApiModelProperty(value = "UL标记")
    @NotNull(message = "UL标记不能为空")
    private String ulMarker;

    @ApiModelProperty(value = "ROHS标记")
    @NotNull(message = "ROHS标记不能为空")
    private String rohsMarker;

    @ApiModelProperty(value = "绿油")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "绿油格式错误，只能输入数字")
    @NotNull(message = "绿油不能为空")
    private String greenOil;

    @ApiModelProperty(value = "碳桥")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "碳桥格式错误，只能输入数字")
    @NotNull(message = "碳桥不能为空")
    private String carbonBridge;

    @ApiModelProperty(value = "标记位置")
    private String markPosition;

    @ApiModelProperty(value = "样板确认日期yyyy-MM-dd")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "样板确认日期不能为空")
    private String confirmDate;

    @ApiModelProperty(value = "建档日期yyyy-MM-dd")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "建档日期不能为空")
    private String recordDate;

    @ApiModelProperty(value = "大料尺寸")
    private String sheetSize;

    @ApiModelProperty(value = "更改日期yyyy-MM-dd")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    private String changeDate;

    @ApiModelProperty(value = "更改依据")
    private String changeBasis;

    @ApiModelProperty(value = "大料PCS数")
    @NotBlank(message = "大料PCS数不能为空")
    @Pattern(regexp = Constants.POSITIVE_NUMBER_PATTERN, message = "大料PCS数格式错误，只能输入大于0的数字")
    private String pcsNumber;

    @ApiModelProperty(value = "大料利用率")
    @NotBlank(message = "大料利用率不能为空")
    @Pattern(regexp = Constants.NUMBER_POINT_PATTERN, message = "大料利用率格式错误，只能输入正整数或小数")
    private String utilizationRatio;

    @ApiModelProperty(value = "A(尺寸)")
    @NotBlank(message = "A(尺寸)不能为空")
    private String miA;

    @ApiModelProperty(value = "B(尺寸)")
    @NotBlank(message = "B(尺寸)不能为空")
    private String miB;

    @ApiModelProperty(value = "C(尺寸)")
    @NotBlank(message = "C(尺寸)不能为空")
    private String miC;

    @ApiModelProperty(value = "D(尺寸)")
    @NotBlank(message = "D(尺寸)不能为空")
    private String miD;

    @ApiModelProperty(value = "大料冲次")
    @NotBlank(message = "大料冲次不能为空")
    private String punching;

    @ApiModelProperty(value = "大料PNL数")
    @NotBlank(message = "大料PNL数不能为空")
    @Pattern(regexp = Constants.POSITIVE_NUMBER_PATTERN, message = "大料PNL数格式错误，只能输入大于0的数字")
    private String pnlNumber;

    @ApiModelProperty(value = "注意事项")
    private String attention;

    @ApiModelProperty(value = "是否允许投产（0：否，1：是）默认为1")
    @NotNull(message = "是否允许投产不能为空")
    private Integer production;

    @ApiModelProperty(value = "文件图片表ID")
    private Long commonFileId;

    @ApiModelProperty(value = "MI工序集合")
    private List<AddProcessDTO> processDTOS;
}

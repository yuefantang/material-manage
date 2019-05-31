package com.dongyu.company.web.order.form;

import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 新增补单记录Form
 *
 * @author TYF
 * @date 2019/1/23
 * @since 1.0.0
 */
@Data
@ApiModel("新增补单记录Form")
public class AddPlusOrderForm {

    @ApiModelProperty(value = "原投产单号")
    @NotBlank(message = "原投产单号不能为空")
    private String commissioningCode;

//    @ApiModelProperty(value = "DY编号")
//    @NotBlank(message = "DY编号不能为空")
//    private String orderDyCode;

    @ApiModelProperty(value = "补单日期yyyy-MM-dd")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "补单日期不能为空")
    private String plusOrderDate;

    @ApiModelProperty(value = "交货日期yyyy-MM-dd")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "交货日期不能为空")
    private String deliveryDate;

    @ApiModelProperty(value = "需补单数量")
    @NotNull(message = "需补单数量不能为空")
    private Integer plusOrderNum;

//    @ApiModelProperty(value = "实补单数量")
//    @NotNull(message = "实补单数量不能为空")
//    private Integer factPlusOrderNum;

    @ApiModelProperty(value = "补单率")
    @Pattern(regexp = Constants.NUMBER_POINT_PATTERN, message = "补单率格式错误，只能输入正整数或小数")
    private String plusOrderRate;

    @ApiModelProperty(value = "经济损失")
    @Pattern(regexp = Constants.NUMBER_POINT_PATTERN, message = "经济损失格式错误，只能输入正整数或小数")
    private String economicLoss;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "补单原因")
    private String reason;

    @ApiModelProperty(value = "处罚情况")
    private String punishSituation;

    @ApiModelProperty(value = "余料处理")
    private String surplusTreatment;


}

package com.dongyu.company.web.mould.form;

import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;

/**
 * 模具采购新增form
 *
 * @author TYF
 * @date 2018/11/11
 * @since 1.0.0
 */
@Data
@ApiModel("新增模具采购form")
public class AddMouldForm {

    @ApiModelProperty(value = "DY编号")
    @NotBlank(message = "DY编号不能为空")
    private String dyCode;

    @ApiModelProperty(value = "产品型号")
    @NotBlank(message = "产品型号不能为空")
    private String productModel;

    @ApiModelProperty(value = "长（单位毫米）")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "格式错误，只能输入数字")
    @NotBlank(message = "长不能为空")
    private String length;

    @ApiModelProperty(value = "宽（单位毫米）")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "格式错误，只能输入数字")
    @NotBlank(message = "宽不能为空")
    private String wide;

    @ApiModelProperty(value = "采购数量")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "格式错误，只能输入数字")
    @NotBlank(message = "采购数量不能为空")
    private String purchaseQuantity;

    @ApiModelProperty(value = "单价（单位分）")
    @Pattern(regexp = Constants.NUMBER_POINT_PATTERN, message = "格式错误，只能输入数字")
    @NotBlank(message = "单价不能为空")
    private String price;

    @ApiModelProperty(value = "金额(单位分)")
    @Pattern(regexp = Constants.NUMBER_POINT_PATTERN, message = "格式错误，只能输入数字")
    @NotBlank(message = "金额不能为空")
    private String amount;

    @ApiModelProperty(value = "供应商（下拉列表）")
    @NotBlank(message = "供应商不能为空")
    private String supplier;

    @ApiModelProperty(value = "采购日期yyyy-MM-dd")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "采购日期不能为空")
    private String purchaseDate;

    @ApiModelProperty(value = "所属客户")
    @NotBlank(message = "所属客户")
    private String affiliatedCustomer;

    @ApiModelProperty(value = "模具类型（下拉列表）")
    @NotBlank(message = "模具类型不能为空")
    private String mouldType;

    @ApiModelProperty(value = "一模出几")
    @NotBlank(message = "一模出几不能为空")
    private String number;

    @ApiModelProperty(value = "采购种类")
    @NotBlank(message = "采购种类不能为空")
    private String purchaseType;

    @ApiModelProperty(value = "连接（下拉列表）")
    @NotBlank(message = "连接不能为空")
    private String connect;

    @ApiModelProperty(value = "备注")
    private String remark;
}

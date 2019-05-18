package com.dongyu.company.web.mould.form;

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

    @ApiModelProperty(value = "采购种类(1:模具,2:测试架)")
    @NotNull(message = "采购种类不能为空")
    private Integer purchaseType;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否收费（0：不收费，1：收费）")
    @NotNull(message = "是否收费不能为空")
    private Integer charge;

    @ApiModelProperty(value = "使用状态（1：新购，2：二次采购,3:报废,4:退回客户）")
    @NotNull(message = "使用状态不能为空")
    private Integer usageState;

    @ApiModelProperty(value = "单价（单位元）")
    @NotNull(message = "单价不能为空")
    @Pattern(regexp = Constants.NUMBER_POINT_PATTERN, message = "格式错误，只能输入数字")
    private String mouldPrice;


    @ApiModelProperty(value = "长（单位毫米）")
    // @Pattern(regexp = Constants.NUMBER_PATTERN, message = "格式错误，只能输入数字")
    // @NotBlank(message = "长不能为空")
    private String length;

    @ApiModelProperty(value = "宽（单位毫米）")
    //@Pattern(regexp = Constants.NUMBER_PATTERN, message = "格式错误，只能输入数字")
    // @NotBlank(message = "宽不能为空")
    private String wide;

    @ApiModelProperty(value = "模具类型（下拉列表）")
    private String mouldType;

    @ApiModelProperty(value = "一模出几")
    // @Pattern(regexp = Constants.NUMBER_PATTERN, message = "格式错误，只能输入数字")
    // @NotBlank(message = "一模出几不能为空")
    private String number;

    @ApiModelProperty(value = "连接（下拉列表）")
    //@NotBlank(message = "连接不能为空")
    private String connect;

    @ApiModelProperty(value = "测试架类型")
    private String testRackType;

    @ApiModelProperty(value = "点数")
    private String point;

    @ApiModelProperty(value = "气缸")
    private String cylinder;

    //    @ApiModelProperty(value = "采购数量")
//    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "格式错误，只能输入数字")
//    @NotBlank(message = "采购数量不能为空")
//    private String purchaseQuantity;

//    @ApiModelProperty(value = "测试架单价（单位元）")
//    @Pattern(regexp = Constants.NUMBER_POINT_PATTERN, message = "格式错误，只能输入数字")
//    private String rackPrice;

//    @ApiModelProperty(value = "测试架金额(单位元)")
//    @Pattern(regexp = Constants.NUMBER_POINT_PATTERN, message = "格式错误，只能输入数字")
//    private String rackAmount;

    //    @ApiModelProperty(value = "模具金额(单位元)")
//    @Pattern(regexp = Constants.NUMBER_POINT_PATTERN, message = "格式错误，只能输入数字")
//    private String mouldAmount;

}

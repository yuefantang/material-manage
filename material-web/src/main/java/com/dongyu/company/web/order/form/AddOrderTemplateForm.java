package com.dongyu.company.web.order.form;

import com.dongyu.company.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

/**
 * 新增样板Form
 *
 * @author TYF
 * @date 2019/1/12
 * @since 1.0.0
 */
@Data
@ApiModel("新增样板Form")
public class AddOrderTemplateForm {


    @ApiModelProperty(value = "样板编号")
    @NotBlank(message = "DY编号不能为空")
    private String templateCode;

    @ApiModelProperty(value = "客户名称")
    @NotBlank(message = "客户名称不能为空")
    private String customerName;

    @ApiModelProperty(value = "客户型号")
    @NotBlank(message = "客户型号不能为空")
    private String customerModel;

    @ApiModelProperty(value = "类型")
    @NotNull(message = "类型不能为空")
    private String templateType;

    @ApiModelProperty(value = "板材")
    @NotNull(message = "板材不能为空")
    private String board;

    @ApiModelProperty(value = "板厚")
    @NotNull(message = "板厚不能为空")
    private String plateThick;

    @ApiModelProperty(value = "铜厚")
    @NotNull(message = "铜厚不能为空")
    private String copperThick;

    @ApiModelProperty(value = "数量")
    @NotNull(message = "数量不能为空")
    private String templateNum;

    @ApiModelProperty(value = "面积")
    @NotNull(message = "面积不能为空")
    private String areaNum;

    @ApiModelProperty(value = "长")
    @NotNull(message = "长不能为空")
    private String templateLength;

    @ApiModelProperty(value = "宽")
    @NotNull(message = "宽不能为空")
    private String templateWide;

    @ApiModelProperty(value = "下单日期")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "下单日期不能为空")
    private String orderDate;

    @ApiModelProperty(value = "备注")
    private String templateRemark;

    @ApiModelProperty(value = "领取人")
    @NotNull(message = "领取人不能为空")
    private String receiver;

    @ApiModelProperty(value = "收费开单状态（0：未收费开单，1：已收费开单）")
    @NotNull(message = "是否收费不能为空")
    private Integer chargeOpening;

    @ApiModelProperty(value = "出货日期")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "出货日期不能为空")
    private String templateDeliveryDate;

    @ApiModelProperty(value = "是否收费（0：不收费，1：收费）")
    @NotNull(message = "是否收费不能为空")
    private Integer charge;
}

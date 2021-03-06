package com.dongyu.company.web.deliverynote.form;

import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;

/**
 * 新增其它收费开单Form
 *
 * @author TYF
 * @date 2018/12/5
 * @since 1.0.0
 */
@Data
@ApiModel("新增其它收费开单Form")
public class AddOtherDeliveryNoteForm {

    @ApiModelProperty(value = "送货数量")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "送货数量格式错误，只能输入数字")
    @NotBlank(message = "送货数量不能为空")
    private String deliveryNum;

    @ApiModelProperty(value = "送货日期yyyy-MM-dd")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "送货日期不能为空")
    private String deliveryDate;

    @ApiModelProperty(value = "投产单号")
    private String commissioningCode;

    @ApiModelProperty(value = "货款开单备注")
    private String deliveryRemarks;

    @ApiModelProperty(value = "DY编号")
    private String miDyCode;

    @ApiModelProperty(value = "客户型号")
    private String customerModel;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户订单号")
    private String customerOrderCode;

//    @ApiModelProperty(value = "送货单号")
//    private String deliveryCode;

    @ApiModelProperty(value = "单价（单位分）")
    @Pattern(regexp = Constants.NUMBER_POINT_PATTERN, message = "格式错误，只能输入数字")
    //@NotBlank(message = "单价不能为空")
    private String price;

    @ApiModelProperty(value = "金额(单位分)")
    @Pattern(regexp = Constants.NUMBER_POINT_PATTERN, message = "格式错误，只能输入数字")
    //@NotBlank(message = "金额不能为空")
    private String amount;

    @ApiModelProperty(value = "单位")
    private String deliveryUnit;

    @ApiModelProperty(value = "下单、模具、测试架或样板数据ID")
    private Long otherId;

    @ApiModelProperty(value = "开单收费类型（1:模具收费,2:样板收费,3:其它收费,4:订单收费,5:测试架）")
    private Integer chargeType;

    @ApiModelProperty(value = "领取人")
    @NotBlank(message = "领取人不能为空")
    private String receiver;
}

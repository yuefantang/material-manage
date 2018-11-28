package com.dongyu.company.web.order.form;

import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;

/**
 * 新增下单Form
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
@Data
@ApiModel("新增下单Form")
public class AddOrderForm {

    @ApiModelProperty(value = "DY编号")
    @NotBlank(message = "DY编号不能为空")
    private String orderDyCode;

    @ApiModelProperty(value = "订单数量")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "订单数量格式错误，只能输入数字")
    @NotBlank(message = "订单数量不能为空")
    private String orderNum;

    @ApiModelProperty(value = "客户订单号")
    @NotBlank(message = "客户订单号不能为空")
    private String ustomercOrderCode;

    @ApiModelProperty(value = "下单日期yyyy-MM-dd")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "下单日期不能为空")
    private String orderDate;

    @ApiModelProperty(value = "交货日期yyyy-MM-dd")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "交货日期不能为空")
    private String deliveryDate;

    @ApiModelProperty(value = "备品率")
    @Pattern(regexp = Constants.NUMBER_POINT_PATTERN, message = "备品率格式错误，只能输入正整数或小数")
    @NotBlank(message = "备品率")
    private String sparePartsRate;

}

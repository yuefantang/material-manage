package com.dongyu.company.web.finance.form;

import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;

/**
 * 新增MI登记价格Form
 *
 * @author TYF
 * @date 2018/12/5
 * @since 1.0.0
 */
@Data
@ApiModel("新增MI登记价格Form")
public class AddMiPriceForm {

    @ApiModelProperty(value = "DY编号")
    @NotBlank(message = "DY编号不能为空")
    private String miDyCode;

    @ApiModelProperty(value = "单价（单位分）")
    @Pattern(regexp = Constants.POSITIVE_NUMBER_PATTERN, message = "单价格式错误，只能输入数字")
    @NotBlank(message = "单价（单位分）不能为空")
    private String price;

    @ApiModelProperty(value = "单价类型")
    @NotBlank(message = "单价类型不能为空")
    private String priceType;

    @ApiModelProperty(value = "报价日期yyyy-MM-dd")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "报价日期不能为空")
    private String quotationDate;

    @ApiModelProperty(value = "是否开票(0：否，1：是)")
    @NotBlank(message = "是否开票不能为空")
    private String isTicket;

    @ApiModelProperty(value = "付款方式")
    @NotBlank(message = "付款方式不能为空")
    private String payType;

    @ApiModelProperty(value = "备注")
    private String remark;

}

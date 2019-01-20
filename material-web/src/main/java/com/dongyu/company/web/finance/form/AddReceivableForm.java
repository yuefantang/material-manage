package com.dongyu.company.web.finance.form;

import com.dongyu.company.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

/**
 * 新增收款Form
 *
 * @author TYF
 * @date 2019/1/20
 * @since 1.0.0
 */
@Data
@ApiModel("新增收款Form")
public class AddReceivableForm {

    @ApiModelProperty(value = "客户名称")
    @NotBlank(message = "客户名称不能为空")
    private String customerName;

    @ApiModelProperty(value = "款项年月份")
    @NotBlank(message = "款项年月份不能为空")
    private String fundMonth;

    @ApiModelProperty(value = "进款金额")
    @NotNull(message = "进款金额不能为空")
    private Double receivableAmount;

    @ApiModelProperty(value = "收款日期")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "收款日期不能为空")
    private String receivableDate;

    @ApiModelProperty(value = "备注")
    private String remark;
}

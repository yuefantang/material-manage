package com.dongyu.company.web.quality.form;

import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 新增品质问题Form
 *
 * @author TYF
 * @date 2019/1/6
 * @since 1.0.0
 */
@Data
@ApiModel("新增品质问题Form")
public class AddQualityForm {

    @ApiModelProperty(value = "DY编号")
    @NotBlank(message = "DY编号不能为空")
    private String dyCode;

    @ApiModelProperty(value = "客户名称")
    @NotBlank(message = "客户名称不能为空")
    private String customerName;

    @ApiModelProperty(value = "产品型号")
    @NotBlank(message = "产品型号不能为空")
    private String customerModel;

    @ApiModelProperty(value = "报废数")
    @NotNull(message = "报废数不能为空")
    @Range(min = 0, max = 9999999)
    private Integer scrapNum;

    @ApiModelProperty(value = "订单数")
    @Range(min = 0, max = 9999999)
    @NotNull(message = "订单数")
    private Integer orderNum;

    @ApiModelProperty(value = "报废率")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "格式错误，只能输入数字")
    @NotBlank(message = "报废率不能为空")
    private String scrapRate;

    @ApiModelProperty(value = "经济损失")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "格式错误，只能输入数字")
    @NotBlank(message = "经济损失不能为空")
    private String economicLoss;

    @ApiModelProperty(value = "问题出现时间yyyy-MM-dd")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "问题出现时间不能为空")
    private String problemTime;

    @ApiModelProperty(value = "报废原因")
    @NotBlank(message = "报废原因不能为空")
    private String scrapReason;
}

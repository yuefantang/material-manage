package com.dongyu.company.web.finance.form;

import com.dongyu.company.common.constants.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

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
    @Pattern(regexp = Constants.NUMBER_POINT_PATTERN, message = "单价格式错误，只能输入数字")
    @NotBlank(message = "单价（单位分）不能为空")
    private String price;
}

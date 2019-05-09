package com.dongyu.company.web.deliverynote.form;

import com.dongyu.company.common.constants.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 新增货款单Form
 *
 * @author TYF
 * @date 2018/12/4
 * @since 1.0.0
 */
@Data
@ApiModel("新增货款单Form")
public class AddDeliveryNoteForm {

    @ApiModelProperty(value = "送货数量")
    @Pattern(regexp = Constants.NUMBER_PATTERN, message = "送货数量格式错误，只能输入数字")
    @NotBlank(message = "送货数量不能为空")
    private String deliveryNum;

//    @ApiModelProperty(value = "送货日期yyyy-MM-dd")
//    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
//    @NotBlank(message = "送货日期不能为空")
//    private String deliveryDate;

    @ApiModelProperty(value = "投产单号")
    @NotBlank(message = "投产单号不能为空")
    private String commissioningCode;

    @ApiModelProperty(value = "DY编号")
    @NotBlank(message = "DY编号不能为空")
    private String dyCode;

    @ApiModelProperty(value = "货款开单备注")
    private String deliveryRemarks;

}

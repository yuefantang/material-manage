package com.dongyu.company.web.deliverynote.form;

import com.dongyu.company.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 编辑货款单Form
 *
 * @author TYF
 * @date 2018/12/6
 * @since 1.0.0
 */
@Data
@ApiModel("编辑货款单Form")
public class EditDeliveryForm extends AddOtherDeliveryNoteForm {

    @ApiModelProperty(value = "货款单ID")
    private Long id;

    @ApiModelProperty(value = "送货日期yyyy-MM-dd")
    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_YYYY_MM_DD)
    @NotBlank(message = "送货日期不能为空")
    private String deliveryDate;
}

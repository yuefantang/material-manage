package com.dongyu.company.deliverynote.dto;

import com.dongyu.company.common.dto.PageQueryDTO;
import lombok.Data;

/**
 * 货款单和账单明细分页查询DTO
 *
 * @author TYF
 * @date 2018/12/4
 * @since 1.0.0
 */
@Data
public class DeliveryQueryDTO extends PageQueryDTO {

    //送货单号
    private String deliveryCode;

    //送货单是否作废(0：否，1：是)
    private Integer deleted;

    //DY编号
    private String miDyCode;

    //送货日期开始时间
    private String deliveryDateStart;

    //送货日期结束时间
    private String deliveryDateEnd;

    //客户名称
    private String customerName;

    //对账月份
    private String billMonth;

    //核实状态（0：未核实，1：已核实），默认-1查全部
    private Integer verifyState;

    //客户型号
    private String customerModel;

    //收费种类
    private String chargeType;

}

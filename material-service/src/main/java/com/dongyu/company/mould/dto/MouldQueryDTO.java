package com.dongyu.company.mould.dto;

import com.dongyu.company.common.dto.PageQueryDTO;
import lombok.Data;

/**
 * 模具采购分页查询DTO
 *
 * @author TYF
 * @date 2018/11/11
 * @since 1.0.0
 */
@Data
public class MouldQueryDTO extends PageQueryDTO {

    //DY编号
    private String dyCode;

    //产品型号
    private String productModel;

    //供应商
    private String supplier;

    //采购日期开始(yyyy-MM-d)
    private String purchaseDateStart;

    //采购日期结束(yyyy-MM-d)
    private String purchaseDateEnd;

    //所属客户
    private String affiliatedCustomer;

    //模具采购是否删除（0：未删除，1：已删除），默认0
    private Integer deleted;

    //是否收费（0：不收费，1：收费），默认0
    private Integer charge;

    //收费开单（0：未收费开单，1：已收费开单）默认0
    private Integer chargeOpening;

    //采购种类(1:模具,2:测试架)
    private Integer purchaseType;
}

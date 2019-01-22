package com.dongyu.company.deliverynote.dao;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.constants.VerifyStateEnum;
import com.dongyu.company.deliverynote.domain.DeliveryNote;
import com.dongyu.company.deliverynote.dto.DeliveryQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义货款单查询
 *
 * @author TYF
 * @date 2018/12/4
 * @since 1.0.0
 */
@Slf4j
public class DeliverySpecs {
    private static final String DELIVERY_DY_CODE = "deliveryCode";
    private static final String DELETED = "deleted";
    private static final String MI_DY_CODE = "miDyCode";
    private static final String DELIVERY_DATE = "deliveryDate";
    private static final String CUSTOMER_NAME = "customerName";
    private static final String BILL_MONTH = "billMonth";
    private static final String VERIFY_STATE = "verifyState";


    public static Specification<DeliveryNote> orederQuerySpec(DeliveryQueryDTO queryDTO) {
        log.info("DeliverySpecs orederQuerySpec method start Parm:" + JSONObject.toJSONString(queryDTO));
        return (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            //根据送货单号模糊查询
            if (StringUtils.isNotBlank(queryDTO.getDeliveryCode())) {
                list.add(builder.like(root.get(DELIVERY_DY_CODE), "%" + queryDTO.getDeliveryCode() + "%"));
            }

            //根据DY编号模糊查询
            if (StringUtils.isNotBlank(queryDTO.getMiDyCode())) {
                list.add(builder.like(root.get(MI_DY_CODE), "%" + queryDTO.getMiDyCode() + "%"));
            }

            //根据客户名称模糊查询
            if (StringUtils.isNotBlank(queryDTO.getCustomerName())) {
                list.add(builder.like(root.get(CUSTOMER_NAME), "%" + queryDTO.getCustomerName() + "%"));
            }

            //根据送货日期开始查询
            if (StringUtils.isNotBlank(queryDTO.getDeliveryDateStart())) {
                //大于或等于传入时间
                list.add(builder.greaterThanOrEqualTo(root.get(DELIVERY_DATE).as(String.class), queryDTO.getDeliveryDateStart()));
            }
            //根据送货日期结束查询
            if (StringUtils.isNotBlank(queryDTO.getDeliveryDateEnd())) {
                //小于等于传入时间
                list.add(builder.lessThanOrEqualTo(root.get(DELIVERY_DATE).as(String.class), queryDTO.getDeliveryDateEnd()));
            }

            //根据送货单是否作废查询
            if (queryDTO.getDeleted()!=null){
            if (queryDTO.getDeleted() == DeletedEnum.UNDELETED.getValue()) {
                //未作废查询
                list.add(builder.equal(root.get(DELETED), queryDTO.getDeleted()));
            } else if (queryDTO.getDeleted() == DeletedEnum.DELETED.getValue()) {
                //作废查询
                list.add(builder.equal(root.get(DELETED), queryDTO.getDeleted()));
            }
            }
            //根据对账月份查询
            if (StringUtils.isNotBlank(queryDTO.getBillMonth())) {
                list.add(builder.equal(root.get(BILL_MONTH), queryDTO.getBillMonth()));
            }

            //根据对账核实状态查询
            if (queryDTO.getVerifyState()!=null){
            if (queryDTO.getVerifyState() == VerifyStateEnum.UNVERIFY.getValue()) {
                //未核实查询
                list.add(builder.equal(root.get(VERIFY_STATE), queryDTO.getVerifyState()));
            } else if (queryDTO.getVerifyState() == VerifyStateEnum.VERIFY.getValue()) {
                //已核实查询
                list.add(builder.equal(root.get(VERIFY_STATE), queryDTO.getVerifyState()));
            }
            }
            log.info("DeliverySpecs orederQuerySpec method end;");
            return builder.and(list.toArray(new Predicate[list.size()]));
        };
    }
}

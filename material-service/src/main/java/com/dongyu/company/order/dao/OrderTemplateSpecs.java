package com.dongyu.company.order.dao;

import com.dongyu.company.common.constants.CurrencyEunm;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.order.domain.OrderTemplate;
import com.dongyu.company.order.dto.OrderTemplateQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义样板查询
 *
 * @author TYF
 * @date 2019/1/12
 * @since 1.0.0
 */
public class OrderTemplateSpecs {

    private static final String CUSTOMER_NAME = "customerName";
    private static final String DELETED = "deleted";
    private static final String DY_CODE = "dyCode";
    private static final String CHARGE_OPENING = "chargeOpening";

    public static Specification<OrderTemplate> orederTemplateSpec(OrderTemplateQueryDTO dto) {
        return (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            //根据客户名称模糊查询
            if (StringUtils.isNotBlank(dto.getCustomerName())) {
                list.add(builder.like(root.get(CUSTOMER_NAME), "%" + dto.getCustomerName() + "%"));
            }

            //根据DY编号模糊查询
            if (StringUtils.isNotBlank(dto.getDyCode())) {
                list.add(builder.like(root.get(DY_CODE), "%" + dto.getDyCode() + "%"));
            }

            //根据下单是否删除查询
            if (dto.getDeleted() == DeletedEnum.UNDELETED.getValue()) {
                //未删除
                list.add(builder.equal(root.get(DELETED), dto.getDeleted()));
            } else if (dto.getDeleted() == DeletedEnum.DELETED.getValue()) {
                //已删除
                list.add(builder.equal(root.get(DELETED), dto.getDeleted()));
            }
            //收费开单
            if (dto.getChargeOpening() != null) {
                if (dto.getChargeOpening() == CurrencyEunm.NO.getValue()) {
                    //未开单
                    list.add(builder.equal(root.get(CHARGE_OPENING), dto.getChargeOpening()));
                } else if (dto.getChargeOpening() == CurrencyEunm.NO.getValue()) {
                    //已开单
                    list.add(builder.equal(root.get(CHARGE_OPENING), dto.getChargeOpening()));
                }
            }

            return builder.and(list.toArray(new Predicate[list.size()]));
        };
    }

}

package com.dongyu.company.order.dao;

import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.order.domain.Order;
import com.dongyu.company.order.dto.OrderQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义下单查询
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
public class OrderSpecs {

    private static final String COMMISSIONING_CODE = "commissioningCode";
    private static final String DELETED = "deleted";
    private static final String COMPLETE_STATE = "completeState";


    public static Specification<Order> orederQuerySpec(OrderQueryDTO orderQueryDTO) {
        return (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            //根据投产单号模糊查询
            if (StringUtils.isNotBlank(orderQueryDTO.getCommissioningCode())) {
                list.add(builder.like(root.get(COMMISSIONING_CODE), "%" + orderQueryDTO.getCommissioningCode() + "%"));
            }
            //订单是否完成
            if (orderQueryDTO.getCompleteState() != null) {
                list.add(builder.equal(root.get(COMPLETE_STATE), orderQueryDTO.getCompleteState()));
            }
            //未删除订单
            list.add(builder.equal(root.get(DELETED), DeletedEnum.UNDELETED.getValue()));
            return builder.and(list.toArray(new Predicate[list.size()]));
        };
    }
}

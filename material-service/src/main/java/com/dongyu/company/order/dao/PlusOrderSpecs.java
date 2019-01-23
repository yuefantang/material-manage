package com.dongyu.company.order.dao;

import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.order.domain.PlusOrder;
import com.dongyu.company.order.dto.PlusOrderQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 补单自定义查询
 *
 * @author TYF
 * @date 2019/1/23
 * @since 1.0.0
 */
public class PlusOrderSpecs {

    private static final String DELETED = "deleted";
    private static final String ORDER_DY_CODE = "orderDyCode";
    private static final String PLUS_CODE = "plusCommissioningCode";

    public static Specification<PlusOrder> plusOrderSpec(PlusOrderQueryDTO dto) {
        return (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            //根据补单单号模糊查询
            if (StringUtils.isNotBlank(dto.getPlusCommissioningCode())) {
                list.add(builder.like(root.get(PLUS_CODE), dto.getPlusCommissioningCode() + "%"));
            }

            //根据DY编号模糊查询
            if (StringUtils.isNotBlank(dto.getOrderDyCode())) {
                list.add(builder.like(root.get(ORDER_DY_CODE), dto.getOrderDyCode() + "%"));
            }

            //根据下单是否删除查询
            if (dto.getDeleted() != null) {
                if (dto.getDeleted() == DeletedEnum.UNDELETED.getValue()) {
                    //未删除
                    list.add(builder.equal(root.get(DELETED), dto.getDeleted()));
                } else if (dto.getDeleted() == DeletedEnum.DELETED.getValue()) {
                    //已删除
                    list.add(builder.equal(root.get(DELETED), dto.getDeleted()));
                }
            }
            return builder.and(list.toArray(new Predicate[list.size()]));
        };
    }
}

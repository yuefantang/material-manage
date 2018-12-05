package com.dongyu.company.deliverynote.dao;

import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.deliverynote.domain.DeliveryNote;
import com.dongyu.company.deliverynote.dto.DeliveryQueryDTO;
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
public class DeliverySpecs {
    private static final String DELIVERY_DY_CODE = "deliveryDyCode";
    private static final String DELETED = "deleted";

    public static Specification<DeliveryNote> orederQuerySpec(DeliveryQueryDTO queryDTO) {
        return (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            //根据送货单号模糊查询
            if (StringUtils.isNotBlank(queryDTO.getDeliveryNumber())) {
                list.add(builder.like(root.get(DELIVERY_DY_CODE), "%" + queryDTO.getDeliveryNumber() + "%"));
            }
            //未删除货款单
            list.add(builder.equal(root.get(DELETED), DeletedEnum.UNDELETED.getValue()));
            return builder.and(list.toArray(new Predicate[list.size()]));
        };
    }
}

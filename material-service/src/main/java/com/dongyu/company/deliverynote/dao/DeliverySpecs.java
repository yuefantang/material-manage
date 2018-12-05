package com.dongyu.company.deliverynote.dao;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.DeletedEnum;
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

    public static Specification<DeliveryNote> orederQuerySpec(DeliveryQueryDTO queryDTO) {
        log.info("DeliverySpecs orederQuerySpec method start Parm:" + JSONObject.toJSONString(queryDTO));
        return (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            //根据送货单号模糊查询
            if (StringUtils.isNotBlank(queryDTO.getDeliveryCode())) {
                list.add(builder.like(root.get(DELIVERY_DY_CODE), "%" + queryDTO.getDeliveryCode() + "%"));
            }

            //根据送货单是否作废查询
            if (queryDTO.getDeleted() == DeletedEnum.UNDELETED.getValue()) {
                //未作废查询
                list.add(builder.equal(root.get(DELETED), queryDTO.getDeleted()));
            } else if (queryDTO.getDeleted() == DeletedEnum.DELETED.getValue()) {
                //作废查询
                list.add(builder.equal(root.get(DELETED), queryDTO.getDeleted()));
            }
            log.info("DeliverySpecs orederQuerySpec method end;");
            return builder.and(list.toArray(new Predicate[list.size()]));
        };
    }
}

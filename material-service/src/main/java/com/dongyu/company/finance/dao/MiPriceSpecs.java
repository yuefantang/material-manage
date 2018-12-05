package com.dongyu.company.finance.dao;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.finance.domain.MiPrice;
import com.dongyu.company.finance.dto.MiPriceQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义MI登记价格查询
 *
 * @author TYF
 * @date 2018/12/5
 * @since 1.0.0
 */
@Slf4j
public class MiPriceSpecs {

    private static final String MI_DY_CODE = "miDyCode";
    private static final String DELETED = "deleted";

    public static Specification<MiPrice> miPriceQuerySpec(MiPriceQueryDTO queryDTO) {
        log.info("MiPriceSpecs miPriceQuerySpec method start Parm:" + JSONObject.toJSONString(queryDTO));
        return (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            //根据DY编号模糊查询
            if (StringUtils.isNotBlank(queryDTO.getMiDyCode())) {
                list.add(builder.like(root.get(MI_DY_CODE), "%" + queryDTO.getMiDyCode() + "%"));
            }

            //根据MI登记价格是否删除
            if (queryDTO.getDeleted() == DeletedEnum.UNDELETED.getValue()) {
                //未删除
                list.add(builder.equal(root.get(DELETED), queryDTO.getDeleted()));
            } else if (queryDTO.getDeleted() == DeletedEnum.DELETED.getValue()) {
                //已删除
                list.add(builder.equal(root.get(DELETED), queryDTO.getDeleted()));
            }
            log.info("DeliverySpecs orederQuerySpec method end;");
            return builder.and(list.toArray(new Predicate[list.size()]));
        };
    }
}

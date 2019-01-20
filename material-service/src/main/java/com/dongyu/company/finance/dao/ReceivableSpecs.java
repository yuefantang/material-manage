package com.dongyu.company.finance.dao;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.finance.domain.Receivable;
import com.dongyu.company.finance.dto.ReceivableQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义收款查询
 *
 * @author TYF
 * @date 2019/1/20
 * @since 1.0.0
 */
@Slf4j
public class ReceivableSpecs {

    private static final String CUSTOMER_NAME = "customerName";
    private static final String FUND_MONTH = "fundMonth";
    private static final String DELETED = "deleted";

    public static Specification<Receivable> receivableQuerySpec(ReceivableQueryDTO queryDTO) {
        log.info("ReceivableSpecs receivableQuerySpec method start Parm:" + JSONObject.toJSONString(queryDTO));
        return (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            //根据客户名称模糊查询
            if (StringUtils.isNotBlank(queryDTO.getCustomerName())) {
                list.add(builder.like(root.get(CUSTOMER_NAME), queryDTO.getCustomerName() + "%"));
            }

            //根据款项年月份查询
            if (StringUtils.isNotBlank(queryDTO.getFundMonth())) {
                list.add(builder.equal(root.get(FUND_MONTH), queryDTO.getFundMonth()));
            }

            //根据收款是否删除
            if (queryDTO.getDeleted() == DeletedEnum.UNDELETED.getValue()) {
                //未删除
                list.add(builder.equal(root.get(DELETED), queryDTO.getDeleted()));
            } else if (queryDTO.getDeleted() == DeletedEnum.DELETED.getValue()) {
                //已删除
                list.add(builder.equal(root.get(DELETED), queryDTO.getDeleted()));
            }
            log.info("ReceivableSpecs receivableQuerySpec method end;");
            return builder.and(list.toArray(new Predicate[list.size()]));
        };
    }
}

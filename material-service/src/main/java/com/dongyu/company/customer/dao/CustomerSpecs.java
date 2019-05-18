package com.dongyu.company.customer.dao;

import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.customer.domain.Customer;
import com.dongyu.company.customer.dto.CustomerQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义客户查询
 *
 * @author TYF
 * @date 2019/5/11
 * @since 1.0.0
 */
public class CustomerSpecs {

    private static final String CUSTOMER_NAME = "customerName";
    private static final String IS_DELETED = "deleted";
    private static final String CUSTOMER_TYPE = "customerType";

    public static Specification<Customer> CustomerListQuerySpec(CustomerQueryDTO dto) {
        return (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            //根据客户模糊查询
            if (StringUtils.isNotBlank(dto.getCustomerName())) {
                list.add(builder.like(root.get(CUSTOMER_NAME), "%" + dto.getCustomerName().trim() + "%"));
            }
            //客户类型
            if (StringUtils.isNotBlank(dto.getCustomerType())) {
                list.add(builder.equal(root.get(CUSTOMER_TYPE), dto.getCustomerType()));
            }
            //是否删除
            if (dto.getDeleted() != null) {
                if (dto.getDeleted() == DeletedEnum.UNDELETED.getValue()) {
                    //未删除
                    list.add(builder.equal(root.get(IS_DELETED), dto.getDeleted()));
                } else if (dto.getDeleted() == DeletedEnum.DELETED.getValue()) {
                    //已删除
                    list.add(builder.equal(root.get(IS_DELETED), dto.getDeleted()));
                }
            }
            return builder.and(list.toArray(new Predicate[list.size()]));
        };
    }
}

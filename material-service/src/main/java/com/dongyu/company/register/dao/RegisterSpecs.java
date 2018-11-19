package com.dongyu.company.register.dao;

import com.dongyu.company.register.domain.MiRegister;
import com.dongyu.company.register.dto.RegisterQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义MI登记查询
 *
 * @author TYF
 * @date 2018/11/17
 * @since 1.0.0
 */
public class RegisterSpecs {
    private static final String MI_DY_CODE = "miDyCode";

    public static Specification<MiRegister> registerQuerySpec(RegisterQueryDTO registerQueryDTO) {
        return (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            //根据DY编号模糊查询
            if (StringUtils.isNotBlank(registerQueryDTO.getMiDyCode())) {
                list.add(builder.like(root.get(MI_DY_CODE), "%" + registerQueryDTO.getMiDyCode() + "%"));
            }
            return builder.and(list.toArray(new Predicate[list.size()]));
        };
    }
}
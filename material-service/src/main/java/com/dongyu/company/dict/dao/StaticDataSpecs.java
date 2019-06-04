package com.dongyu.company.dict.dao;

import com.dongyu.company.dict.domain.StaticData;
import com.dongyu.company.dict.dto.StaticQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义下来列表数据查询
 *
 * @author TYF
 * @date 2019/6/4
 * @since 1.0.0
 */
public class StaticDataSpecs {

    private static final String CODE_TYPE = "codeType";
    private static final String DELETED = "deleted";
    private static final String CODE_DESC = "codeDesc";
    private static final String CODE_KEY = "codeKey";
    private static final String CODE_VALUE = "codeValue";

    public static Specification<StaticData> staticDataSpec(StaticQueryDTO dto) {
        return (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            //根据数据类型模糊查询
            if (StringUtils.isNotBlank(dto.getCodeType())) {
                list.add(builder.like(root.get(CODE_TYPE), "%" + dto.getCodeType() + "%"));
            }

            //根据数据描述模糊查询
            if (StringUtils.isNotBlank(dto.getCodeDesc())) {
                list.add(builder.like(root.get(CODE_DESC), "%" + dto.getCodeDesc() + "%"));
            }

            //根据是否删除查询
            if (dto.getDeleted() != null) {
                list.add(builder.equal(root.get(DELETED), dto.getDeleted()));
            }

            //数据key
            if (StringUtils.isNotBlank(dto.getCodeKey())) {
                list.add(builder.equal(root.get(CODE_KEY), dto.getCodeKey()));
            }

            //数据key对应的值
            if (StringUtils.isNotBlank(dto.getCodeValue())) {
                list.add(builder.like(root.get(CODE_VALUE), "%" + dto.getCodeValue() + "%"));
            }

            return builder.and(list.toArray(new Predicate[list.size()]));
        };
    }

}

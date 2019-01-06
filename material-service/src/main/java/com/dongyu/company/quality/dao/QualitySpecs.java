package com.dongyu.company.quality.dao;

import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.quality.domain.Quality;
import com.dongyu.company.quality.dto.QualityQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义品质问题查询
 *
 * @author TYF
 * @date 2019/1/6
 * @since 1.0.0
 */
public class QualitySpecs {
    private static final String DY_CODE = "dyCode";
    private static final String DELETED = "deleted";

    public static Specification<Quality> qualityListQuerySpec(QualityQueryDTO queryDTO) {
        return (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            //根据DY编号模糊查询
            if (StringUtils.isNotBlank(queryDTO.getDyCode())) {
                list.add(builder.like(root.get(DY_CODE), "%" + queryDTO.getDyCode() + "%"));
            }

            //是否删除查询
            if (queryDTO.getDeleted() != null) {
                if (queryDTO.getDeleted() == DeletedEnum.UNDELETED.getValue()) {
                    //未删除
                    list.add(builder.equal(root.get(DELETED), queryDTO.getDeleted()));
                } else if (queryDTO.getDeleted() == DeletedEnum.DELETED.getValue()) {
                    //已删除
                    list.add(builder.equal(root.get(DELETED), queryDTO.getDeleted()));
                }
            }
            return builder.and(list.toArray(new Predicate[list.size()]));
        };
    }

}

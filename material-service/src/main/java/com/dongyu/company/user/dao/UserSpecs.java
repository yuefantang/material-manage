package com.dongyu.company.user.dao;

import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.user.domain.User;
import com.dongyu.company.user.dto.UserQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


/**
 * 自定义用户查询
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
public class UserSpecs {
    private static final String USER_NAME = "userName";
    private static final String IS_DELETED = "isDeleted";

    public static Specification<User> userListQuerySpec(UserQueryDTO userQueryDTO) {
        return (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            //根据用户名模糊查询
            if (StringUtils.isNotBlank(userQueryDTO.getUserName())) {
                list.add(builder.like(root.get(USER_NAME), "%" + userQueryDTO.getUserName() + "%"));
            }
            list.add(builder.equal(root.get(IS_DELETED), Constants.USER_NOT_DELETED));
            return builder.and(list.toArray(new Predicate[list.size()]));
        };
    }
}

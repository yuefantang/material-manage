package com.dongyu.company.user.dao;

import com.dongyu.company.user.domain.User;
import com.dongyu.company.user.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户与角色关系数据处理
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
public interface UserRoleDao extends JpaRepository<UserRole, Long> {

    //根据用户查找对应的用户角色关系
    UserRole findByUser(User user);
}

package com.dongyu.company.user.dao;

import com.dongyu.company.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 角色数据层处理
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
public interface RoleDao extends JpaRepository<Role, Long> {

    //根据角色id获取角色
    Role findOneById(Long roleId);
}

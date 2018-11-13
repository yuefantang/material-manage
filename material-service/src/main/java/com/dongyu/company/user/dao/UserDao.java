package com.dongyu.company.user.dao;

import com.dongyu.company.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户数据层处理
 *
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUserNameAndIsDeleted(String userName, Boolean isDeleted);

    User findOneById(Long id);


}

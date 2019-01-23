package com.dongyu.company.user.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户角色关联表
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "t_user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", columnDefinition = "bigint(20) COMMENT '用户表ID'")
    private Long userId;

    @Column(name = "role_id", columnDefinition = "bigint(20) COMMENT '角色表ID'")
    private Long roleId;

}

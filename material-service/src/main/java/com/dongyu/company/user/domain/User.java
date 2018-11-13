package com.dongyu.company.user.domain;

import com.dongyu.company.common.domain.BaseDomain;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户实体类
 *
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "t_user")
@EntityListeners({AuditingEntityListener.class})
public class User extends BaseDomain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private boolean isDeleted = false;


}

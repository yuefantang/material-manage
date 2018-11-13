package com.dongyu.company.user.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 角色名称
     */
    @NotBlank
    @Column(unique = true)
    private String roleName;


    /**
     * 角色描述
     */
    @NotBlank
    @Column(length = 40, unique = true)
    private String descript;

}

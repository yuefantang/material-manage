package com.dongyu.company.order.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 余料处理表
 *
 * @author TYF
 * @date 2018/11/28
 * @since 1.0.0
 */

@Data
@Entity
@Table(name = "t_surplus")
@EntityListeners({AuditingEntityListener.class})
public class Surplus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column(columnDefinition = "varchar(255) COMMENT '余料处理方法'")
    private String surplusTreatment;

    @Column(columnDefinition = "varchar(255) COMMENT '余料处理备注'")
    private String surplusRemarks;

    @Column(columnDefinition = "varchar(50) COMMENT '余料PCS'")
    private String surplusPcs;

    @Column(columnDefinition = "varchar(50) COMMENT '余料PNL'")
    private String surplusPnl;

    @Column(columnDefinition = "tinyint(4) COMMENT '数据操作状态（1：新增，2：修改）'")
    private Integer operationState;
}

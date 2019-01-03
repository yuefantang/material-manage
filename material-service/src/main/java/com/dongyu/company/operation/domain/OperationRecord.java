package com.dongyu.company.operation.domain;

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

/**
 * TODO:请添加描述
 *
 * @author TYF
 * @date 2019/1/3
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "t_operation_record")
@EntityListeners({AuditingEntityListener.class})
public class OperationRecord extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column(columnDefinition = "varchar(255) COMMENT '修改之前的值'")
    private String oldValue;

    @Column(columnDefinition = "varchar(255) COMMENT '修改之后的值'")
    private String newValue;

    @Column(columnDefinition = "varchar(255) COMMENT '修改的字段名称'")
    private String attribute;

    @Column(columnDefinition = "bigint(20) COMMENT '修改数据所在表的id'")
    private Long entityId;

    @Column(columnDefinition = "varchar(255) COMMENT '修改数据所在表名称'")
    private String entity;

    @Column(columnDefinition = "varchar(255) COMMENT '操作类型'")
    private String operationType;

}

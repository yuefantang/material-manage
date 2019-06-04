package com.dongyu.company.dict.domain;

import com.dongyu.company.common.annotation.AttributeOpName;
import com.dongyu.company.common.annotation.AttributeOpRecord;
import com.dongyu.company.common.constants.DeletedEnum;
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
 * 静态数据配置表
 *
 * @author TYF
 * @date 2019/5/14
 * @since 1.0.0
 */
@Entity
@Data
@AttributeOpRecord
@Table(name = "t_static_data")
@EntityListeners({AuditingEntityListener.class})
public class StaticData extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @AttributeOpName("数据类型")
    @Column(columnDefinition = "varchar(255) COMMENT '数据类型'")
    private String codeType;

    @AttributeOpName("数据key")
    @Column( columnDefinition = "varchar(255) COMMENT '数据key'")
    private String codeKey;

    @AttributeOpName("数据key对应的值")
    @Column(columnDefinition = "varchar(255) COMMENT '数据key对应的值'")
    private String codeValue;

    @AttributeOpName("数据描述")
    @Column(columnDefinition = "varchar(255) COMMENT '数据描述'")
    private String codeDesc;

    @AttributeOpName("状态")
    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单是否删除（0：未删除，1：已删除）'")
    private Integer deleted = DeletedEnum.UNDELETED.getValue();
}

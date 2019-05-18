package com.dongyu.company.customer.domain;

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
import java.io.Serializable;

/**
 * 客户实体类
 *
 * @author TYF
 * @date 2019/5/11
 * @since 1.0.0
 */
@Data
@Entity
@AttributeOpRecord
@Table(name = "t_customer")
@EntityListeners({AuditingEntityListener.class})
public class Customer extends BaseDomain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column(columnDefinition = "varchar(50) COMMENT '客户名称'")
    @AttributeOpName("客户名称")
    private String customerName;

    @Column(columnDefinition = "varchar(50) COMMENT '客户类型(supplier:供应商，customer：客户)'")
    @AttributeOpName("客户类型")
    private String customerType;

    @Column(columnDefinition = "varchar(50) COMMENT '客户联系方式'")
    @AttributeOpName("客户联系方式")
    private String customerContact;

    @Column(columnDefinition = "varchar(200) COMMENT '客户地址'")
    @AttributeOpName("客户地址")
    private String customerAddress;

    @AttributeOpName("删除")
    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT '客户是否删除（0：未删除，1：已删除）'")
    private Integer deleted = DeletedEnum.UNDELETED.getValue();
}

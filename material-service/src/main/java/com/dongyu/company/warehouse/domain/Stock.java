package com.dongyu.company.warehouse.domain;

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
 * 库存实体类
 *
 * @author TYF
 * @date 2019/1/22
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "t_stock")
@AttributeOpRecord
@EntityListeners({AuditingEntityListener.class})
public class Stock extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column(unique = true, columnDefinition = "varchar(50) COMMENT 'DY编号'")
    @AttributeOpName("DY编号")
    private String dyCode;

    @Column(columnDefinition = "varchar(50) COMMENT '客户型号'")
    @AttributeOpName("客户型号")
    private String customerModel;

    @Column(columnDefinition = "varchar(100) COMMENT '客户名称'")
    @AttributeOpName("客户名称")
    private String customerName;

    @Column(columnDefinition = "int(11) COMMENT '库存数量'")
    @AttributeOpName("库存数量")
    private Integer stockNum;

    @Column(columnDefinition = "varchar(255) COMMENT '备注'")
    @AttributeOpName("备注")
    private String remark;

    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT 'MI登记是否删除（0：未删除，1：已删除）'")
    @AttributeOpName("删除")
    private Integer deleted = DeletedEnum.UNDELETED.getValue();

}

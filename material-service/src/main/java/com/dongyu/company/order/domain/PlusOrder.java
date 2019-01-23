package com.dongyu.company.order.domain;

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
import java.util.Date;

/**
 * 补单实体表
 *
 * @author TYF
 * @date 2019/1/23
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "t_plus_order")
@AttributeOpRecord
@EntityListeners({AuditingEntityListener.class})
public class PlusOrder extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column(unique = true, columnDefinition = "varchar(50) COMMENT '补单单号'")
    private String plusCommissioningCode;

    @Column(columnDefinition = "varchar(50) COMMENT '原产单号'")
    @AttributeOpName("原产单号")
    private String commissioningCode;

    @Column(columnDefinition = "varchar(50) COMMENT '产品型号'")
    @AttributeOpName("产品型号")
    private String customerModel;

    @Column(columnDefinition = "varchar(100) COMMENT '客户名称'")
    @AttributeOpName("客户名称")
    private String customerName;

    @Column(columnDefinition = "varchar(50) COMMENT 'DY编号'")
    @AttributeOpName("DY编号")
    private String orderDyCode;

    @Column(columnDefinition = "datetime COMMENT '补单日期'")
    @AttributeOpName("补单日期")
    private Date plusOrderDate;

    @Column(columnDefinition = "datetime COMMENT '交货日期'")
    @AttributeOpName("交货日期")
    private Date deliveryDate;

    @Column(columnDefinition = "double(16,3) COMMENT '平方数'")
    @AttributeOpName("平方数")
    private double squareNum;

    @Column(columnDefinition = "int(11) COMMENT '需补单数量'")
    @AttributeOpName("需补单数量")
    private Integer plusOrderNum;

    @Column(columnDefinition = "int(11) COMMENT '实补单数量'")
    @AttributeOpName("实补单数量")
    private Integer factPlusOrderNum;

    @Column(columnDefinition = "double(16,3) COMMENT '补单率'")
    @AttributeOpName("补单率")
    private double plusOrderRate;

    @Column(columnDefinition = "double(16,3) COMMENT '经济损失'")
    @AttributeOpName("经济损失")
    private double economicLoss;

    @Column(columnDefinition = "varchar(255) COMMENT '备注'")
    @AttributeOpName("备注")
    private String remark;

    @Column(columnDefinition = "varchar(255) COMMENT '补单原因'")
    @AttributeOpName("补单原因")
    private String reason;

    @Column(columnDefinition = "varchar(255) COMMENT '处罚情况'")
    @AttributeOpName("处罚情况")
    private String punishSituation;

    @Column(columnDefinition = "varchar(255) COMMENT '余料处理'")
    @AttributeOpName("余料处理")
    private String surplusTreatment;

    @AttributeOpName("删除")
    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单是否删除（0：未删除，1：已删除）'")
    private Integer deleted = DeletedEnum.UNDELETED.getValue();

}

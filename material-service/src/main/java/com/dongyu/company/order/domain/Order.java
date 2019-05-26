package com.dongyu.company.order.domain;

import com.dongyu.company.common.annotation.AttributeOpName;
import com.dongyu.company.common.annotation.AttributeOpRecord;
import com.dongyu.company.common.constants.CompleteStateEnum;
import com.dongyu.company.common.constants.CurrencyEunm;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.domain.BaseDomain;
import com.dongyu.company.register.domain.MiRegister;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * 下单实体表
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "t_order")
@AttributeOpRecord
@EntityListeners({AuditingEntityListener.class})
public class Order extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column(unique = true, columnDefinition = "varchar(50) COMMENT '投产单号'")
    private String commissioningCode;

    @Column(columnDefinition = "varchar(50) COMMENT 'DY编号'")
    @AttributeOpName("DY编号")
    private String orderDyCode;

    @Column(columnDefinition = "varchar(50) COMMENT '订单数量'")
    @AttributeOpName("订单数量")
    private String orderNum;

    @Column(columnDefinition = "varchar(50) COMMENT '客户订单号'")
    @AttributeOpName("客户订单号")
    private String customerOrderCode;

    @Column(columnDefinition = "datetime COMMENT '下单日期'")
    @AttributeOpName("下单日期")
    private Date orderDate;

    @Column(columnDefinition = "datetime COMMENT '交货日期'")
    @AttributeOpName("交货日期")
    private Date deliveryDate;

    @Column(columnDefinition = "varchar(50) COMMENT '备品率(单位千分之几)'")
    @AttributeOpName("备品率")
    private String sparePartsRate;

    @Column(columnDefinition = "varchar(50) COMMENT '备品数'")
    @AttributeOpName("备品数")
    private String sparePartsNum;

    @Column(columnDefinition = "varchar(50) COMMENT '平方数'")
    @AttributeOpName("平方数")
    private String squareNum;

    @Column(columnDefinition = "varchar(50) COMMENT '投产数量'")
    @AttributeOpName("投产数量")
    private String commissioningNum;

    @AttributeOpName("删除")
    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单是否删除（0：未删除，1：已删除）'")
    private Integer deleted = DeletedEnum.UNDELETED.getValue();

    @Column(columnDefinition = "tinyint(4) COMMENT '数据操作状态（1：新增，2：修改）'")
    @AttributeOpName("数据操作状态")
    private Integer operationState;

    @Column(columnDefinition = "varchar(50) NOT NULL DEFAULT '0' COMMENT '已完成数量'")
    @AttributeOpName("已完成数量")
    private String completedNum;

    @Column(columnDefinition = "varchar(50) NOT NULL DEFAULT '0' COMMENT '未完成数量'")
    @AttributeOpName("未完成数量")
    private String uncompletedNum;

    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT '下单完成状态（0：未完成，1：完成）'")
    @AttributeOpName("下单完成状态")
    private Integer completeState = CompleteStateEnum.UNCOMPLETE.getValue();

    //    @OneToOne
    //    @JoinColumn(name = "surplus_id", columnDefinition = "bigint(20) COMMENT '余料处理ID'")
//    @Column(columnDefinition = "bigint(20) COMMENT '余料处理ID'")
//    private Long surplusId;

    @Column(columnDefinition = "varchar(255) COMMENT '余料处理方法'")
    private String surplusTreatment;

    @Column(columnDefinition = "varchar(255) COMMENT '余料处理备注'")
    private String surplusRemarks;

    @Column(columnDefinition = "varchar(50) COMMENT '余料PCS'")
    private String surplusPcs;

    @Column(columnDefinition = "varchar(50) COMMENT '余料PNL'")
    private String surplusPnl;

//    @ManyToOne
//    @JoinColumn(name = "mi_register_id", columnDefinition = "bigint(20) COMMENT 'MI登记ID'")
//    private MiRegister miRegister;

    @Column(columnDefinition = "bigint(20) COMMENT 'MI登记ID'")
    private Long miRegisterId;

    @Column(columnDefinition = "varchar(50) NOT NULL DEFAULT '0' COMMENT '共用料张数'")
    @AttributeOpName("共用料张数")
    private String haredMaterialsNum;

    @Column(columnDefinition = "tinyint(4) COMMENT '收费开单状态（0：未收费开单，1：已收费开单）'")
    @AttributeOpName("收费开单状态")
    private Integer chargeOpening = CurrencyEunm.NO.getValue();
}

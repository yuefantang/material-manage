package com.dongyu.company.mould.domain;

import com.dongyu.company.common.annotation.AttributeOpName;
import com.dongyu.company.common.annotation.AttributeOpRecord;
import com.dongyu.company.common.constants.CurrencyEunm;
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
 * 模具采购表
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "t_purchase_mould")
@AttributeOpRecord
@EntityListeners({AuditingEntityListener.class})
public class PurchaseMould extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column(unique = true, columnDefinition = "varchar(30) COMMENT 'DY编号'")
    @AttributeOpName("DY编号")
    private String dyCode;

    @Column(columnDefinition = "varchar(100) COMMENT '产品型号'")
    @AttributeOpName("产品型号")
    private String productModel;

    @Column(columnDefinition = "varchar(100) COMMENT '长（单位毫米）'")
    @AttributeOpName("长")
    private String length;

    @Column(columnDefinition = "varchar(100) COMMENT '宽（单位毫米）'")
    @AttributeOpName("宽")
    private String wide;

    @Column(columnDefinition = "varchar(100) COMMENT '采购数量'")
    @AttributeOpName("采购数量")
    private String purchaseQuantity;

    @Column(columnDefinition = "varchar(100) COMMENT '模具单价（单位元）'")
    @AttributeOpName("模具单价")
    private String mouldPrice;

    @Column(columnDefinition = "varchar(100) COMMENT '模具金额(单位元)'")
    @AttributeOpName("模具金额")
    private String mouldAmount;

    @Column(columnDefinition = "varchar(100) COMMENT '测试架单价（单位元）'")
    @AttributeOpName("测试架单价")
    private String rackPrice;

    @Column(columnDefinition = "varchar(100) COMMENT '测试架金额(单位元)'")
    @AttributeOpName("测试架金额")
    private String rackAmount;

    @Column(columnDefinition = "varchar(100) COMMENT '供应商'")
    @AttributeOpName("供应商")
    private String supplier;

    @Column(columnDefinition = "datetime COMMENT '采购日期'")
    @AttributeOpName("采购日期")
    private Date purchaseDate;

    @Column(columnDefinition = "varchar(100) COMMENT '所属客户'")
    @AttributeOpName("所属客户")
    private String affiliatedCustomer;

    @Column(columnDefinition = "varchar(100) COMMENT '模具类型'")
    @AttributeOpName("模具类型")
    private String mouldType;

    @Column(columnDefinition = "varchar(20) COMMENT '一模出几'")
    @AttributeOpName("一模出几")
    private String number;

    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT '采购种类(1:模具,2:测试架,3:模具-测试架)'")
    @AttributeOpName("采购种类")
    private Integer purchaseType;

    @Column(columnDefinition = "varchar(10) COMMENT '连接'")
    @AttributeOpName("连接")
    private String connect;

    @Column(columnDefinition = "varchar(255) COMMENT '连接'")
    @AttributeOpName("连接")
    private String remark;

    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT '模具是否删除（0：未删除，1：已删除）'")
    @AttributeOpName("删除")
    private Integer deleted = DeletedEnum.UNDELETED.getValue();

    @Column(columnDefinition = "tinyint(4) COMMENT '是否收费（0：不收费，1：收费）'")
    @AttributeOpName("是否收费")
    private Integer charge = CurrencyEunm.NO.getValue();

    @Column(columnDefinition = "tinyint(4) COMMENT '收费开单状态（0：未收费开单，1：已收费开单）'")
    @AttributeOpName("收费开单状态")
    private Integer chargeOpening = CurrencyEunm.NO.getValue();
}

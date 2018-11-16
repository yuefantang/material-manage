package com.dongyu.company.mould.domain;

import com.dongyu.company.common.domain.BaseDomain;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
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
@EntityListeners({AuditingEntityListener.class})
public class PurchaseMould extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column(unique = true, columnDefinition = "varchar(30) COMMENT 'DY编号'")
    private String dyCode;

    @Column(columnDefinition = "varchar(100) COMMENT '产品型号'")
    private String productModel;

    @Column(columnDefinition = "varchar(100) COMMENT '长（单位毫米）'")
    private String length;

    @Column(columnDefinition = "varchar(100) COMMENT '宽（单位毫米）'")
    private String wide;

    @Column(columnDefinition = "varchar(100) COMMENT '采购数量'")
    private String purchaseQuantity;

    @Column(columnDefinition = "varchar(100) COMMENT '单价（单位分）'")
    private String price;

    @Column(columnDefinition = "varchar(100) COMMENT '金额(单位分)'")
    private String amount;

    @Column(columnDefinition = "varchar(100) COMMENT '供应商'")
    private String supplier;

    @Column(columnDefinition = "datetime COMMENT '采购日期'")
    private Date purchaseDate;

    @Column(columnDefinition = "varchar(100) COMMENT '所属客户'")
    private String affiliatedCustomer;

    @Column(columnDefinition = "varchar(100) COMMENT '模具类型'")
    private String mouldType;

    @Column(columnDefinition = "varchar(20) COMMENT '一模出几'")
    private String number;

    @Column(columnDefinition = "varchar(100) COMMENT '采购种类'")
    private String purchaseType;

    @Column(columnDefinition = "varchar(10) COMMENT '连接'")
    private String connect;

    @Column(columnDefinition = "varchar(255) COMMENT '备注'")
    private String remark;

}

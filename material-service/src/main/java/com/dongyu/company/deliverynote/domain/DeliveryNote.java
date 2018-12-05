package com.dongyu.company.deliverynote.domain;

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
 * 货款单实体类
 *
 * @author TYF
 * @date 2018/12/4
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "t_delivery_note")
@EntityListeners({AuditingEntityListener.class})
public class DeliveryNote extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column(columnDefinition = "varchar(100) COMMENT '送货数量'")
    private String deliveryNum;

    @Column(columnDefinition = "varchar(50) COMMENT '送货单号'")
    private String deliveryCode;

    @Column(columnDefinition = "datetime COMMENT '送货日期'")
    private Date deliveryDate;

    @Column(columnDefinition = "varchar(255) COMMENT '货款开单备注'")
    private String deliveryRemarks;

    @Column(columnDefinition = "varchar(50) COMMENT 'DY编号'")
    private String miDyCode;

    @Column(columnDefinition = "varchar(50) COMMENT '客户型号'")
    private String customerModel;

    @Column(columnDefinition = "varchar(100) COMMENT '客户名称'")
    private String customerName;

    @Column(columnDefinition = "varchar(50) COMMENT '客户订单号'")
    private String customerOrderCode;

    @Column(columnDefinition = "varchar(50) COMMENT '投产单号'")
    private String commissioningCode;

    @Column(columnDefinition = "varchar(100) COMMENT '单价（单位分）'")
    private String price;

    @Column(columnDefinition = "varchar(100) COMMENT '金额(单位分)'")
    private String amount;

    @Column(columnDefinition = "varchar(20) COMMENT '单位'")
    private String deliveryUnit;

}

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
    @Column(name = "id")
    private Long id;

    //DY编号
    @Length(max = 30)
    @Column(name = "dy_code", unique = true)
    private String dyCode;

    //产品型号
    @Length(max = 100)
    private String productModel;

    //长（单位毫米）
    @Length(max = 100)
    private String length;

    //宽（单位毫米）
    @Length(max = 100)
    private String wide;

    //采购数量
    @Length(max = 100)
    private String purchaseQuantity;

    //单价（单位分）
    @Length(max = 100)
    private String price;

    //金额(单位分)
    @Length(max = 100)
    private String amount;

    //供应商
    @Length(max = 50)
    private String supplier;

    //采购日期
    private Date purchaseDate;

    //所属客户
    @Length(max = 50)
    private String affiliatedCustomer;

    //模具类型
    @Length(max = 50)
    private String mouldType;

    //一模出几
    @Length(max = 50)
    private String number;

    //采购种类
    @Length(max = 50)
    private String purchaseType;

    //连接
    @Length(max = 10)
    private String connect;

    //备注
    @Length(max = 255)
    private String remark;

}

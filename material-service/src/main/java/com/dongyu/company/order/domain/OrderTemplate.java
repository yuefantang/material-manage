package com.dongyu.company.order.domain;

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
 * 样板实体类
 *
 * @author TYF
 * @date 2019/1/12
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "t_order_template")
@AttributeOpRecord
@EntityListeners({AuditingEntityListener.class})
public class OrderTemplate extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

//    @Column(unique = true, columnDefinition = "varchar(50) COMMENT '样板编号'")
//    @AttributeOpName("样板编号")
//    private String templateCode;

    @Column(unique = true, columnDefinition = "varchar(30) COMMENT 'DY编号'")
    @AttributeOpName("DY编号")
    private String dyCode;

    @Column(columnDefinition = "int COMMENT '数量'")
    @AttributeOpName("数量")
    private Integer templateNum;

    @Column(columnDefinition = "double(16,3) COMMENT '面积'")
    @AttributeOpName("面积")
    private double areaNum;

    @Column(columnDefinition = "double(16,3) COMMENT '长'")
    @AttributeOpName("长")
    private double templateLength;

    @Column(columnDefinition = "double(16,3) COMMENT '宽'")
    @AttributeOpName("宽")
    private double templateWide;

    @Column(columnDefinition = "double(16,3) COMMENT '厚度'")
    @AttributeOpName("厚度")
    private double thickness;

    @Column(columnDefinition = "varchar(100) COMMENT '类型'")
    @AttributeOpName("类型")
    private String templateType;

    @Column(columnDefinition = "varchar(255) COMMENT '备注'")
    @AttributeOpName("备注")
    private String templateRemark;

    @Column(columnDefinition = "varchar(100) COMMENT '领取人'")
    @AttributeOpName("领取人")
    private String receiver;

    @Column(columnDefinition = "varchar(100) COMMENT '板材'")
    @AttributeOpName("板材")
    private String board;

    @Column(columnDefinition = "varchar(100) COMMENT '客户名称'")
    @AttributeOpName("客户名称")
    private String customerName;

    @Column(columnDefinition = "varchar(100) COMMENT '客户型号'")
    @AttributeOpName("客户型号")
    private String customerModel;

    @Column(columnDefinition = "datetime COMMENT '出货日期'")
    @AttributeOpName("出货日期")
    private Date templateDeliveryDate;

    @AttributeOpName("删除")
    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单是否删除（0：未删除，1：已删除）'")
    private Integer deleted = DeletedEnum.UNDELETED.getValue();

    @Column(columnDefinition = "tinyint(4) COMMENT '收费开单状态（0：未收费开单，1：已收费开单）'")
    @AttributeOpName("收费开单状态")
    private Integer chargeOpening = CurrencyEunm.NO.getValue();

}

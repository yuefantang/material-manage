package com.dongyu.company.deliverynote.domain;

import com.dongyu.company.common.annotation.AttributeOpName;
import com.dongyu.company.common.annotation.AttributeOpRecord;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.constants.VerifyStateEnum;
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
@AttributeOpRecord
@EntityListeners({AuditingEntityListener.class})
public class DeliveryNote extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column(columnDefinition = "varchar(100) COMMENT '送货数量'")
    @AttributeOpName("送货数量")
    private String deliveryNum;

    @Column(columnDefinition = "varchar(50) COMMENT '送货单号'")
    @AttributeOpName("送货单号")
    private String deliveryCode;

    @Column(columnDefinition = "datetime COMMENT '送货日期'")
    @AttributeOpName("送货日期")
    private Date deliveryDate;

    @Column(columnDefinition = "varchar(255) COMMENT '货款开单备注'")
    @AttributeOpName("货款开单备注")
    private String deliveryRemarks;

    @Column(columnDefinition = "varchar(50) COMMENT 'DY编号'")
    @AttributeOpName("DY编号")
    private String miDyCode;

    @Column(columnDefinition = "varchar(50) COMMENT '客户型号'")
    @AttributeOpName("客户型号")
    private String customerModel;

    @Column(columnDefinition = "varchar(100) COMMENT '客户名称'")
    @AttributeOpName("客户名称")
    private String customerName;

    @Column(columnDefinition = "varchar(50) COMMENT '客户订单号'")
    @AttributeOpName("客户订单号")
    private String customerOrderCode;

    @Column(columnDefinition = "varchar(50) COMMENT '投产单号'")
    @AttributeOpName("投产单号")
    private String commissioningCode;

    @Column(columnDefinition = "varchar(100) COMMENT '单价（单位分）'")
    @AttributeOpName("单价")
    private String price;

    @Column(columnDefinition = "varchar(100) COMMENT '金额(单位分)'")
    @AttributeOpName("金额")
    private String amount;

    @Column(columnDefinition = "varchar(20) COMMENT '单位'")
    @AttributeOpName("单位")
    private String deliveryUnit;

    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT '送货单是否作废（0：否，1：是）'")
    @AttributeOpName("删除")
    private Integer deleted = DeletedEnum.UNDELETED.getValue();

//    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT '开单类型（1：货款开单，2：其它收费开单）'")
//    private Integer billingType;

    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT '开单收费类型（1, 模具收费，2：样板收费，3：其它收费，4：订单收费,5：测试架收费）'")
    private Integer chargeType;

    @Column(columnDefinition = "varchar(20) COMMENT '对账月份'")
    private String billMonth;

    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT '核实状态（0：未核实，1：已核实）'")
    private Integer verifyState = VerifyStateEnum.UNVERIFY.getValue();

    @Column(columnDefinition = "varchar(20) COMMENT '领取人'")
    private String receiver;

    @Column(columnDefinition = "tinyint(4) COMMENT '是否全部完成(0否，1是；默认否不勾选)）'")
    private Integer isComplement;

    @Column(columnDefinition = "varchar(20) COMMENT '全部完成说明'")
    private String complementExplain;

    @Column(columnDefinition = "bigint(20) COMMENT '下单、模具、测试架或样板数据ID'")
    private Long otherId;


}

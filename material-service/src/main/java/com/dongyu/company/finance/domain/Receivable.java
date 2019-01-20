package com.dongyu.company.finance.domain;

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
 * 收款相关表
 *
 * @author TYF
 * @date 2019/1/20
 * @since 1.0.0
 */

@Data
@Entity
@Table(name = "t_receivable")
@AttributeOpRecord
@EntityListeners({AuditingEntityListener.class})
public class Receivable  extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column( columnDefinition = "varchar(50) COMMENT '客户名称'")
    @AttributeOpName("客户名称")
    private String customerName;

    @Column( columnDefinition = "varchar(50) COMMENT '款项年月份'")
    @AttributeOpName("款项年月份")
    private String fundMonth;

    @Column( columnDefinition = "double(16,3) COMMENT '进款金额'")
    @AttributeOpName("进款金额")
    private Double receivableAmount;

    @Column( columnDefinition = "datetime COMMENT '收款日期'")
    @AttributeOpName("收款日期")
    private Date receivableDate;

    @Column( columnDefinition = "varchar(255) COMMENT '备注'")
    @AttributeOpName("备注")
    private String remark;

    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT 'MI登记是否删除（0：未删除，1：已删除）'")
    @AttributeOpName("删除")
    private Integer deleted = DeletedEnum.UNDELETED.getValue();

}

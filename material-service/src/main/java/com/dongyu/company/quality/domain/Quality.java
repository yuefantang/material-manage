package com.dongyu.company.quality.domain;

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
 * 品质问题实体类
 *
 * @author TYF
 * @date 2019/1/5
 * @since 1.0.0
 */

@Data
@Entity
@Table(name = "t_quality")
@AttributeOpRecord
@EntityListeners({AuditingEntityListener.class})
public class Quality extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column(unique = true, columnDefinition = "varchar(50) COMMENT 'DY编号'")
    @AttributeOpName("DY编号")
    private String dyCode;

    @Column(columnDefinition = "varchar(50) COMMENT '客户名称'")
    @AttributeOpName("客户名称")
    private String customerName;

    @Column(columnDefinition = "varchar(50) COMMENT '产品型号'")
    @AttributeOpName("产品型号")
    private String customerModel;

    @Column(columnDefinition = "int COMMENT '报废数'")
    @AttributeOpName("报废数")
    private Integer scrapNum;

    @Column(columnDefinition = "int COMMENT '订单数'")
    @AttributeOpName("订单数")
    private Integer orderNum;

    @Column(columnDefinition = "varchar(50) COMMENT '报废率'")
    @AttributeOpName("报废率")
    private String scrapRate;

    @Column(columnDefinition = "varchar(50) COMMENT '经济损失'")
    @AttributeOpName("经济损失")
    private String economicLoss;

    @Column(columnDefinition = "datetime COMMENT '问题出现时间'")
    @AttributeOpName("问题出现时间")
    private Date problemTime;

    @Column(columnDefinition = "varchar(255) COMMENT '报废原因'")
    @AttributeOpName("报废原因")
    private String scrapReason;

    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT 'MI登记是否删除（0：未删除，1：已删除）'")
    @AttributeOpName("删除")
    private Integer deleted = DeletedEnum.UNDELETED.getValue();
}

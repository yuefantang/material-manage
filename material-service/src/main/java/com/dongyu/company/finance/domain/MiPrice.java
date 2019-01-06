package com.dongyu.company.finance.domain;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * MI登记价格表
 *
 * @author TYF
 * @date 2018/12/5
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "t_mi_price")
@EntityListeners({AuditingEntityListener.class})
public class MiPrice extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column(unique = true, columnDefinition = "varchar(50) COMMENT 'DY编号'")
    private String miDyCode;

    @Column(columnDefinition = "varchar(100) COMMENT '单价（单位分）'")
    private String price;

    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT 'MI对应价格是否删除（0：未删除，1：已删除）'")
    private Integer deleted = DeletedEnum.UNDELETED.getValue();

    @Column(columnDefinition = "bigint(20) COMMENT 'MI登记表ID'")
    private Long miRegisterId;

    //@OneToOne
    //  @JoinColumn(name = "mi_register_id", columnDefinition = "bigint(20) COMMENT 'MI登记表ID'")
  //  private MiRegister miRegister;

}

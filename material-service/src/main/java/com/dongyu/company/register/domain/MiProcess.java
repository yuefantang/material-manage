package com.dongyu.company.register.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * MI工序实体类
 *
 * @author TYF
 * @date 2018/11/15
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "t_mi_process")
public class MiProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column(columnDefinition = "varchar(50) COMMENT '序号'")
    private String orderNumber;

    @Column(columnDefinition = "varchar(50) COMMENT '工序'")
    private String process;

    @Column(columnDefinition = "varchar(255) COMMENT '备注'")
    private String remark;

    @ManyToOne
    @JoinColumn(name = "mi_register_id", columnDefinition = "bigint(20) COMMENT 'MI登记表ID'")
    private MiRegister miRegister;


}

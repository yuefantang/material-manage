package com.dongyu.company.register.domain;

import com.dongyu.company.common.domain.BaseDomain;
import com.dongyu.company.file.domian.CommonFile;
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
import java.util.Date;

/**
 * Mi登记实体类
 *
 * @author TYF
 * @date 2018/11/15
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "t_mi_register")
@EntityListeners({AuditingEntityListener.class})
public class MiRegister extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column(unique = true, columnDefinition = "varchar(50) COMMENT 'DY编号'")
    private String miDyCode;

    @Column(columnDefinition = "varchar(50) COMMENT '客户型号'")
    private String customerModel;

    @Column(columnDefinition = "varchar(100) COMMENT '客户名称'")
    private String customerName;

    @Column(columnDefinition = "varchar(50) COMMENT '客户料号'")
    private String customerMaterialNum;

    @Column(columnDefinition = "varchar(100) COMMENT '板材类型'")
    private String plateType;

    @Column(columnDefinition = "varchar(20) COMMENT '板厚'")
    private String plateThick;

    @Column(columnDefinition = "varchar(20) COMMENT '铜厚'")
    private String copperThick;

    @Column(columnDefinition = "varchar(100) COMMENT '板材商'")
    private String plateMerchant;

    @Column(columnDefinition = "varchar(100) COMMENT '工艺'")
    private String technology;

    @Column(columnDefinition = "varchar(100) COMMENT '工程更改内容'")
    private String changeContent;

    @Column(columnDefinition = "varchar(20) COMMENT '单片尺寸'")
    private String singleSize;

    @Column(columnDefinition = "varchar(20) COMMENT '模片尺寸'")
    private String dieSize;

    @Column(columnDefinition = "varchar(20) COMMENT '一模出几'")
    private String miNumber;

    @Column(columnDefinition = "varchar(100) COMMENT '模具编号'")
    private String moldNumber;

    @Column(columnDefinition = "varchar(50) COMMENT '模具类型'")
    private String mouldType;

    @Column(columnDefinition = "varchar(10) COMMENT '连接'")
    private String miConnect;

    @Column(columnDefinition = "varchar(100) COMMENT '开模商'")
    private String openMoldMerchant;

    @Column(columnDefinition = "datetime COMMENT '开模日期'")
    private Date openMoldDate;

    @Column(columnDefinition = "varchar(100) COMMENT '菲林编号'")
    private String filmNumber;

    @Column(columnDefinition = "tinyint(4) COMMENT '线路'")
    private boolean isLine = false;

    @Column(columnDefinition = "tinyint(4) COMMENT '文字'")
    private boolean isWords = false;

    @Column(columnDefinition = "tinyint(4) COMMENT '其它'")
    private boolean isOther = false;

    @Column(columnDefinition = "tinyint(4) COMMENT '周期标记'")
    private boolean cycleMarker = false;

    @Column(columnDefinition = "tinyint(4) COMMENT 'UL标记'")
    private boolean ulMarker = false;

    @Column(columnDefinition = "tinyint(4) COMMENT 'ROHS标记'")
    private boolean rohsMarker = false;

    @Column(columnDefinition = "tinyint(4) COMMENT '绿油'")
    private boolean isGreenOil = false;

    @Column(columnDefinition = "tinyint(4) COMMENT '碳桥'")
    private boolean isCarbonBridge = false;

    @Column(columnDefinition = "varchar(100) COMMENT '标记位置'")
    private String markPosition;

    @Column(columnDefinition = "datetime COMMENT '样板确认日期'")
    private Date ConfirmDate;

    @Column(columnDefinition = "datetime COMMENT '建档日期'")
    private Date recordDate;

    @Column(columnDefinition = "varchar(20) COMMENT '大料尺寸'")
    private String sheetSize;

    @Column(columnDefinition = "datetime COMMENT '更改日期'")
    private Date changeDate;

    @Column(columnDefinition = "varchar(255) COMMENT '更改依据'")
    private String changeBasis;

    @Column(columnDefinition = "varchar(20) COMMENT '大料PCS数'")
    private String pcsNumber;

    @Column(columnDefinition = "varchar(20) COMMENT '大料利用率'")
    private String utilizationRatio;

    @Column(columnDefinition = "varchar(100) COMMENT 'A(尺寸)'")
    private String miA;

    @Column(columnDefinition = "varchar(100) COMMENT 'B(尺寸)'")
    private String miB;

    @Column(columnDefinition = "varchar(100) COMMENT 'C(尺寸)'")
    private String miC;

    @Column(columnDefinition = "varchar(100) COMMENT 'D(尺寸)'")
    private String miD;

    @Column(columnDefinition = "varchar(20) COMMENT '大料冲次'")
    private String punching;

    @Column(columnDefinition = "varchar(20) COMMENT '大料PNL数'")
    private String pnlNumber;

    @Column(columnDefinition = "varchar(255) COMMENT '注意事项'")
    private String attention;

    @Column(columnDefinition = "tinyint(4) COMMENT '是否允许投产'")
    private boolean isProduction = false;

    @OneToOne
    @JoinColumn(name = "common_file_id", columnDefinition = "bigint(20) COMMENT '文件图片表ID'")
    private CommonFile commonFile;
}

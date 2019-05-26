package com.dongyu.company.register.domain;

import com.dongyu.company.common.annotation.AttributeOpName;
import com.dongyu.company.common.annotation.AttributeOpRecord;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.domain.BaseDomain;
import com.dongyu.company.finance.domain.MiPrice;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
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
@AttributeOpRecord
@EntityListeners({AuditingEntityListener.class})
public class MiRegister extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint(20) COMMENT '主键id'")
    private Long id;

    @Column(unique = true, columnDefinition = "varchar(50) COMMENT 'DY编号'")
    @AttributeOpName("DY编号")
    private String miDyCode;

    @Column(columnDefinition = "varchar(50) COMMENT '客户型号'")
    @AttributeOpName("客户型号")
    private String customerModel;

    @Column(columnDefinition = "varchar(100) COMMENT '客户名称'")
    @AttributeOpName("客户名称")
    private String customerName;

    @Column(columnDefinition = "varchar(50) COMMENT '客户料号'")
    @AttributeOpName("客户料号")
    private String customerMaterialNum;

    @Column(columnDefinition = "varchar(100) COMMENT '板材类型'")
    @AttributeOpName("板材类型")
    private String plateType;

    @Column(columnDefinition = "varchar(20) COMMENT '板厚'")
    @AttributeOpName("板厚")
    private String plateThick;

    @Column(columnDefinition = "varchar(20) COMMENT '铜厚'")
    @AttributeOpName("铜厚")
    private String copperThick;

    @Column(columnDefinition = "varchar(100) COMMENT '板材商'")
    @AttributeOpName("板材商")
    private String plateMerchant;

    @Column(columnDefinition = "varchar(100) COMMENT '工艺'")
    @AttributeOpName("工艺")
    private String technology;

    @Column(columnDefinition = "varchar(100) COMMENT '工程更改内容'")
    @AttributeOpName("工程更改内容")
    private String changeContent;

    @Column(columnDefinition = "double(16,3) COMMENT '单片尺寸长'")
    @AttributeOpName("单片尺寸长")
    private Double singleSizeLength;

    @Column(columnDefinition = "double(16,3) COMMENT '单片尺寸宽'")
    @AttributeOpName("单片尺寸宽")
    private Double singleSizeWide;

    @Column(columnDefinition = "double(16,3) COMMENT '模片尺寸长'")
    @AttributeOpName("模片尺寸长")
    private Double dieSizeLength;

    @Column(columnDefinition = "double(16,3) COMMENT '模片尺寸宽'")
    @AttributeOpName("模片尺寸宽")
    private Double dieSizeWide;

    @Column(columnDefinition = "varchar(20) COMMENT '一模出几'")
    @AttributeOpName("一模出几")
    private String miNumber;

    @Column(columnDefinition = "varchar(100) COMMENT '模具编号'")
    @AttributeOpName("模具编号")
    private String moldNumber;

    @Column(columnDefinition = "varchar(50) COMMENT '模具类型'")
    @AttributeOpName("模具类型")
    private String mouldType;

    @Column(columnDefinition = "varchar(10) COMMENT '连接'")
    @AttributeOpName("连接")
    private String miConnect;

    @Column(columnDefinition = "varchar(100) COMMENT '开模商'")
    @AttributeOpName("开模商")
    private String openMoldMerchant;

    @Column(columnDefinition = "datetime COMMENT '开模日期'")
    @AttributeOpName("开模日期")
    private Date openMoldDate;

    @Column(columnDefinition = "varchar(100) COMMENT '菲林编号'")
    @AttributeOpName("菲林编号")
    private String filmNumber;

    @Column(columnDefinition = "varchar(4) NOT NULL DEFAULT '0' COMMENT '线路'")
    @AttributeOpName("线路")
    private String line;

    @Column(columnDefinition = "varchar(4) NOT NULL DEFAULT '0' COMMENT '文字'")
    @AttributeOpName("文字")
    private String words;

    @Column(columnDefinition = "varchar(4)NOT NULL DEFAULT '0' COMMENT '其它'")
    @AttributeOpName("其它")
    private String other;

    @Column(columnDefinition = "varchar(4) NOT NULL DEFAULT '0' COMMENT '周期标记'")
    @AttributeOpName("周期标记")
    private String cycleMarker;

    @Column(columnDefinition = "varchar(4) NOT NULL DEFAULT '0' COMMENT 'UL标记'")
    @AttributeOpName("UL标记")
    private String ulMarker;

    @Column(columnDefinition = "varchar(4) NOT NULL DEFAULT '0' COMMENT 'ROHS标记'")
    @AttributeOpName("ROHS标记")
    private String rohsMarker;

    @Column(columnDefinition = "varchar(4) NOT NULL DEFAULT '0' COMMENT '绿油'")
    @AttributeOpName("绿油")
    private String greenOil;

    @Column(columnDefinition = "varchar(4 )NOT NULL DEFAULT '0' COMMENT '碳桥'")
    @AttributeOpName("碳桥")
    private String carbonBridge;

    @Column(columnDefinition = "varchar(100) COMMENT '标记位置'")
    @AttributeOpName("标记位置")
    private String markPosition;

    @Column(columnDefinition = "datetime COMMENT '样板确认日期'")
    @AttributeOpName("样板确认日期")
    private Date confirmDate;

    @Column(columnDefinition = "datetime COMMENT '建档日期'")
    @AttributeOpName("建档日期")
    private Date recordDate;

    @Column(columnDefinition = "varchar(20) COMMENT '大料尺寸'")
    @AttributeOpName("大料尺寸")
    private String sheetSize;

    @Column(columnDefinition = "datetime COMMENT '更改日期'")
    @AttributeOpName("更改日期")
    private Date changeDate;

    @Column(columnDefinition = "varchar(255) COMMENT '更改依据'")
    @AttributeOpName("更改依据")
    private String changeBasis;

    @Column(columnDefinition = "varchar(20) COMMENT '大料PCS数'")
    @AttributeOpName("大料PCS数")
    private String pcsNumber;

    @Column(columnDefinition = "varchar(20) COMMENT '大料利用率'")
    @AttributeOpName("大料利用率")
    private String utilizationRatio;

    @Column(columnDefinition = "varchar(100) COMMENT 'A(尺寸)'")
    @AttributeOpName("A(尺寸)")
    private String miA;

    @Column(columnDefinition = "varchar(100) COMMENT 'B(尺寸)'")
    @AttributeOpName("B(尺寸)")
    private String miB;

    @Column(columnDefinition = "varchar(100) COMMENT 'C(尺寸)'")
    @AttributeOpName("C(尺寸)")
    private String miC;

    @Column(columnDefinition = "varchar(100) COMMENT 'D(尺寸)'")
    @AttributeOpName("D(尺寸)")
    private String miD;

    @Column(columnDefinition = "varchar(20) COMMENT '大料冲次'")
    @AttributeOpName("大料冲次")
    private String punching;

    @Column(columnDefinition = "varchar(20) COMMENT '大料PNL数'")
    @AttributeOpName("大料PNL数")
    private String pnlNumber;

    @Column(columnDefinition = "varchar(255) COMMENT '注意事项'")
    @AttributeOpName("注意事项")
    private String attention;

    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否允许投产（0：否，1：是）'")
    @AttributeOpName("是否允许投产")
    private Integer production;

    @Column(columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT 'MI登记是否删除（0：未删除，1：已删除）'")
    @AttributeOpName("删除")
    private Integer deleted = DeletedEnum.UNDELETED.getValue();

    @Column(columnDefinition = "bigint(20) COMMENT '单价表ID'")
    private Long miPriceId;

    @Column(columnDefinition = "bigint(20) COMMENT '文件图片表ID'")
    @AttributeOpName("图片修改")
    private Long commonFileId;
}

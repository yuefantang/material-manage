package com.dongyu.company.register.dto;

import lombok.Data;

import java.util.List;

/**
 * Mi登记新增DTO
 *
 * @author TYF
 * @date 2018/11/16
 * @since 1.0.0
 */
@Data
public class AddRegisterDTO {

    //DY编号
    private String miDyCode;

    //客户型号
    private String customerModel;

    //客户名称
    private String customerName;

    //客户料号
    private String customerMaterialNum;

    //板材类型
    private String plateType;

    //板厚
    private String plateThick;

    //铜厚
    private String copperThick;

    //板材商
    private String plateMerchant;

    //工艺
    private String technology;

    //工程更改内容
    private String changeContent;

    //单片尺寸
    private String singleSize;

    //模片尺寸
    private String dieSize;

    //一模出几
    private String miNumber;

    //模具编号
    private String moldNumber;

    //模具类型
    private String mouldType;

    //连接
    private String miConnect;

    //开模商
    private String openMoldMerchant;

    //开模日期yyyy-MM-dd
    private String openMoldDate;

    //菲林编号
    private String filmNumber;

    //线路
    private boolean isLine = false;

    //文字
    private boolean isWords = false;

    //其它
    private boolean isOther = false;

    //周期标记
    private boolean cycleMarker = false;

    //UL标记
    private boolean ulMarker = false;

    //ROHS标记
    private boolean rohsMarker = false;

    //绿油
    private boolean isGreenOil = false;

    //碳桥
    private boolean isCarbonBridge = false;

    //标记位置
    private String markPosition;

    //样板确认日期yyyy-MM-dd
    private String ConfirmDate;

    //建档日期yyyy-MM-dd
    private String recordDate;

    //大料尺寸
    private String sheetSize;

    //更改日期yyyy-MM-dd
    private String changeDate;

    //更改依据
    private String changeBasis;

    //大料PCS数
    private String pcsNumber;

    //大料利用率
    private String utilizationRatio;

    //A(尺寸)
    private String miA;

    //B(尺寸)
    private String miB;

    //C(尺寸)
    private String miC;

    //D(尺寸)
    private String miD;

    //大料冲次
    private String punching;

    //大料PNL数
    private String pnlNumber;

    //注意事项
    private String attention;

    //是否允许投产
    private boolean isProduction = false;

    //文件图片表ID
    private Long commonFileId;

    //MI工序集合
    private List<AddProcessDTO> processDTOS;

}

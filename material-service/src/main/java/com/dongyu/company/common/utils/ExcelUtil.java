package com.dongyu.company.common.utils;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * excel工具类
 *
 * @author TYF
 * @date 2018/11/13
 * @since 1.0.0
 */
public class ExcelUtil {

    /**
     * 表头单元格样式
     *
     * @param workbook
     * @return
     */
    public static CellStyle titleStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);//字体加粗
        headerFont.setFontName("Times New Roman");
        headerFont.setFontHeightInPoints((short) 11);
        headerStyle.setFont(headerFont);
        headerStyle.setBorderBottom(CellStyle.BORDER_THIN);//下边框
        headerStyle.setBorderLeft(CellStyle.BORDER_THIN); //左边框
        headerStyle.setBorderTop(CellStyle.BORDER_THIN); //上边框
        headerStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框
        return headerStyle;
    }

    /**
     * 值单元格样式
     *
     * @param workbook
     * @return
     */
    public static CellStyle valueStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //垂直对齐居中
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setWrapText(true);//设置为自动换行
        Font cellFont = workbook.createFont();
        cellFont.setFontName("宋体");
        cellFont.setFontHeightInPoints((short) 11);
        cellStyle.setFont(cellFont);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);//下边框
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN); //左边框
        cellStyle.setBorderTop(CellStyle.BORDER_THIN); //上边框
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框
        return cellStyle;
    }


}

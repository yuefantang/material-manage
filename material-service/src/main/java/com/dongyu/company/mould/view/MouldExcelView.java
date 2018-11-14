package com.dongyu.company.mould.view;

import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.ExcelUtil;
import com.dongyu.company.common.view.ExcelView;
import com.dongyu.company.mould.dto.MouldDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 模具采购导出view
 *
 * @author TYF
 * @date 2018/11/13
 * @since 1.0.0
 */
@Slf4j
@Component
public class MouldExcelView extends ExcelView {

    @Override
    protected void setRow(Workbook workbook, Map<String, Object> map) {
        log.info("MouldExcelView setRow method start:");

        //需要导出的数据源
        List<MouldDetailDTO> listDTO = (List<MouldDetailDTO>) map.get("mouldListDTO");

        //导出总的行数
        int totalCount = 0;
        //单个sheet页总行数
        int rowCount = 0;
        //标题行单元格样式
        CellStyle headerCellStyle = ExcelUtil.titleStyle(workbook);
        //值单元格样式
        CellStyle cellStyle = ExcelUtil.valueStyle(workbook);

        Sheet sheet = null;
        while (true) {
            //填充数据
            for (MouldDetailDTO data : listDTO) {
                //导出数量超出excel存储的量抛异常
                if (totalCount > Constants.MAX_XLS_TOTAL_ROW) {
                    throw new BizException("导出内容超出1000000");
                }
                //单个sheet页行数为0或行数超过60000创建新的sheet页
                if (rowCount == 0 || rowCount >= Constants.MAX_XLS_SHEET_ROW) {
                    //创建新的sheet页
                    sheet = workbook.createSheet();
                    //创建标题行
                    Row headerRow = sheet.createRow(0);
                    //设置标题行
                    List<String> headers = new LinkedList<>();
                    headers.addAll(Arrays.asList("DY编号", "产品型号", "长（单位毫米）", "宽（单位毫米）",
                            "采购数量", "单价（单位分）", "金额(单位分)", "供应商", "采购日期", "所属客户",
                            "模具类型", "一模出几", "采购种类", "连接", "备注"));
                    for (int i = 0; i < headers.size(); i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(headers.get(i));
                        cell.setCellStyle(headerCellStyle);
                        //根据列标题长度设置列宽
                        sheet.setColumnWidth(i, headers.get(i).getBytes().length * 2 * 252);
                    }
                    rowCount = 1;
                    totalCount++;
                }

                Row dataRow = sheet.createRow(rowCount);
                int creditNum = 0;

                //DY编号
                Cell cell1 = dataRow.createCell(creditNum++);
                cell1.setCellStyle(cellStyle);
                cell1.setCellValue(Optional.ofNullable(data.getDyCode()).orElse(""));

                //产品型号
                Cell cell2 = dataRow.createCell(creditNum++);
                cell2.setCellStyle(cellStyle);
                cell2.setCellValue(Optional.ofNullable(data.getProductModel()).orElse(""));

                //长（单位毫米）
                Cell cell3 = dataRow.createCell(creditNum++);
                cell3.setCellStyle(cellStyle);
                cell3.setCellValue(Optional.ofNullable(data.getLength()).orElse(""));

                //宽（单位毫米）
                Cell cell4 = dataRow.createCell(creditNum++);
                cell4.setCellStyle(cellStyle);
                cell4.setCellValue(Optional.ofNullable(data.getWide()).orElse(""));

                //采购数量
                Cell cell5 = dataRow.createCell(creditNum++);
                cell5.setCellStyle(cellStyle);
                cell5.setCellValue(Optional.ofNullable(data.getPurchaseQuantity()).orElse(""));

                //单价（单位分）
                Cell cell6 = dataRow.createCell(creditNum++);
                cell6.setCellStyle(cellStyle);
                cell6.setCellValue(Optional.ofNullable(data.getPrice()).orElse(""));

                //金额(单位分)
                Cell cell7 = dataRow.createCell(creditNum++);
                cell7.setCellStyle(cellStyle);
                cell7.setCellValue(Optional.ofNullable(data.getAmount()).orElse(""));

                //供应商
                Cell cell8 = dataRow.createCell(creditNum++);
                cell8.setCellStyle(cellStyle);
                cell8.setCellValue(Optional.ofNullable(data.getSupplier()).orElse(""));

                //采购日期
                Cell cell9 = dataRow.createCell(creditNum++);
                cell9.setCellStyle(cellStyle);
                cell9.setCellValue(Optional.ofNullable(data.getPurchaseDate()).orElse(""));

                //所属客户
                Cell cell10 = dataRow.createCell(creditNum++);
                cell10.setCellStyle(cellStyle);
                cell10.setCellValue(Optional.ofNullable(data.getAffiliatedCustomer()).orElse(""));

                //模具类型
                Cell cell11 = dataRow.createCell(creditNum++);
                cell11.setCellStyle(cellStyle);
                cell11.setCellValue(Optional.ofNullable(data.getMouldType()).orElse(""));

                //一模出几
                Cell cell12 = dataRow.createCell(creditNum++);
                cell12.setCellStyle(cellStyle);
                cell12.setCellValue(Optional.ofNullable(data.getNumber()).orElse(""));

                //采购种类
                Cell cell13 = dataRow.createCell(creditNum++);
                cell13.setCellStyle(cellStyle);
                cell13.setCellValue(Optional.ofNullable(data.getPurchaseType()).orElse(""));

                //连接
                Cell cell14 = dataRow.createCell(creditNum++);
                cell14.setCellStyle(cellStyle);
                cell14.setCellValue(Optional.ofNullable(data.getConnect()).orElse(""));

                //备注
                Cell cell15 = dataRow.createCell(creditNum++);
                cell15.setCellStyle(cellStyle);
                cell15.setCellValue(Optional.ofNullable(data.getRemark()).orElse(""));

                rowCount++;
                totalCount++;
            }
            break;

        }
    }
}

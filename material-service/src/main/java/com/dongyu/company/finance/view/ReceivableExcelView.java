package com.dongyu.company.finance.view;

import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.ExcelUtil;
import com.dongyu.company.common.view.ExcelView;
import com.dongyu.company.finance.dto.ReceivableListDTO;
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
 * 收款导出View
 *
 * @author TYF
 * @date 2019/1/20
 * @since 1.0.0
 */
@Slf4j
@Component
public class ReceivableExcelView extends ExcelView {
    @Override
    protected void setRow(Workbook workbook, Map<String, Object> map) {
        log.info("ReceivableExcelView setRow method start:");

        //需要导出的数据源
        List<ReceivableListDTO> listDTO = (List<ReceivableListDTO>) map.get("receivableListDTO");

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
            for (ReceivableListDTO data : listDTO) {
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
                    headers.addAll(Arrays.asList("客户名称", "款项年月份",
                            "进款金额", "收款日期", "备注"));
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

                //客户名称
                Cell cell1 = dataRow.createCell(creditNum++);
                cell1.setCellStyle(cellStyle);
                cell1.setCellValue(Optional.ofNullable(data.getCustomerName()).orElse(""));

                //款项年月份
                Cell cell2 = dataRow.createCell(creditNum++);
                cell2.setCellStyle(cellStyle);
                cell2.setCellValue(Optional.ofNullable(data.getFundMonth()).orElse(""));

                //进款金额
                Cell cell3 = dataRow.createCell(creditNum++);
                cell3.setCellStyle(cellStyle);
                cell3.setCellValue(Optional.ofNullable(data.getReceivableAmount()).orElse(0d));

                //收款日期
                Cell cell4 = dataRow.createCell(creditNum++);
                cell4.setCellStyle(cellStyle);
                cell4.setCellValue(Optional.ofNullable(data.getReceivableDate()).orElse(""));

                //备注
                Cell cell5 = dataRow.createCell(creditNum++);
                cell5.setCellStyle(cellStyle);
                cell5.setCellValue(Optional.ofNullable(data.getRemark()).orElse(""));

                rowCount++;
                totalCount++;
            }
            break;

        }
    }
}
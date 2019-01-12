package com.dongyu.company.order.view;

import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.ExcelUtil;
import com.dongyu.company.common.view.ExcelView;
import com.dongyu.company.order.dto.OrderDetailDTO;
import com.dongyu.company.order.dto.OrderTemplateDTO;
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
 * 样板导出View
 *
 * @author TYF
 * @date 2019/1/12
 * @since 1.0.0
 */
@Slf4j
@Component
public class OrderTemplateExcelView extends ExcelView {

    @Override
    protected void setRow(Workbook workbook, Map<String, Object> map) {
        log.info("OrderTemplateExcelView setRow method start:");

        //需要导出的数据源
        List<OrderTemplateDTO> listDTO = (List<OrderTemplateDTO>) map.get("orderTemplateListDTO");

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
            for (OrderTemplateDTO data : listDTO) {
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
                    headers.addAll(Arrays.asList("DY编号", "客户名称", "客户型号","长","宽",
                            "数量", "面积", "板材", "厚度", "类型", "出货日期", "领取人","备注"));
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

                //客户名称
                Cell cell2 = dataRow.createCell(creditNum++);
                cell2.setCellStyle(cellStyle);
                cell2.setCellValue(Optional.ofNullable(data.getCustomerName()).orElse(""));

                //客户型号
                Cell cell3 = dataRow.createCell(creditNum++);
                cell3.setCellStyle(cellStyle);
                cell3.setCellValue(Optional.ofNullable(data.getCustomerModel()).orElse(""));

                //长
                Cell cell4 = dataRow.createCell(creditNum++);
                cell4.setCellStyle(cellStyle);
                cell4.setCellValue(Optional.ofNullable(data.getTemplateLength()).orElse(0d));

                //宽
                Cell cell5 = dataRow.createCell(creditNum++);
                cell5.setCellStyle(cellStyle);
                cell5.setCellValue(Optional.ofNullable(data.getTemplateWide()).orElse(0d));

                //数量
                Cell cell6 = dataRow.createCell(creditNum++);
                cell6.setCellStyle(cellStyle);
                cell6.setCellValue(Optional.ofNullable(data.getTemplateNum()).orElse(0));

                //面积
                Cell cell7 = dataRow.createCell(creditNum++);
                cell7.setCellStyle(cellStyle);
                cell7.setCellValue(Optional.ofNullable(data.getAreaNum()).orElse(0d));

                //板材
                Cell cell8 = dataRow.createCell(creditNum++);
                cell8.setCellStyle(cellStyle);
                cell8.setCellValue(Optional.ofNullable(data.getBoard()).orElse(""));

                //厚度
                Cell cell9 = dataRow.createCell(creditNum++);
                cell9.setCellStyle(cellStyle);
                cell9.setCellValue(Optional.ofNullable(data.getThickness()).orElse(0d));

                //类型
                Cell cell10 = dataRow.createCell(creditNum++);
                cell10.setCellStyle(cellStyle);
                cell10.setCellValue(Optional.ofNullable(data.getTemplateType()).orElse(""));

                //出货日期
                Cell cell11 = dataRow.createCell(creditNum++);
                cell11.setCellStyle(cellStyle);
                cell11.setCellValue(Optional.ofNullable(data.getTemplateDeliveryDate()).orElse(""));

                //领取人
                Cell cell12 = dataRow.createCell(creditNum++);
                cell11.setCellStyle(cellStyle);
                cell11.setCellValue(Optional.ofNullable(data.getReceiver()).orElse(""));

                //备注
                Cell cell13 = dataRow.createCell(creditNum++);
                cell11.setCellStyle(cellStyle);
                cell11.setCellValue(Optional.ofNullable(data.getTemplateRemark()).orElse(""));

                rowCount++;
                totalCount++;
            }
            break;

        }
    }
}

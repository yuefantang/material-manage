package com.dongyu.company.order.view;

import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.ExcelUtil;
import com.dongyu.company.common.view.ExcelView;
import com.dongyu.company.order.dto.OrderDetailDTO;
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
 * 下单导出View
 *
 * @author TYF
 * @date 2019/1/5
 * @since 1.0.0
 */
@Slf4j
@Component
public class OrderExcelView extends ExcelView {

    @Override
    protected void setRow(Workbook workbook, Map<String, Object> map) {
        log.info("OrderExcelView setRow method start:");

        //需要导出的数据源
        List<OrderDetailDTO> listDTO = (List<OrderDetailDTO>) map.get("orderDetailDtoS");

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
            for (OrderDetailDTO data : listDTO) {
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
                    headers.addAll(Arrays.asList("投产单号", "DY编号", "客户名称", "客户型号",
                            "订单数量", "客户订单号", "平方数", "已完成数量", "未完成数量", "下单日期", "交货日期","备注"));
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

                //投产单号
                Cell cell1 = dataRow.createCell(creditNum++);
                cell1.setCellStyle(cellStyle);
                cell1.setCellValue(Optional.ofNullable(data.getCommissioningCode()).orElse(""));

                //DY编号
                Cell cell2 = dataRow.createCell(creditNum++);
                cell2.setCellStyle(cellStyle);
                cell2.setCellValue(Optional.ofNullable(data.getOrderDyCode()).orElse(""));

                //客户名称
                Cell cell3 = dataRow.createCell(creditNum++);
                cell3.setCellStyle(cellStyle);
                cell3.setCellValue(Optional.ofNullable(data.getRegisterDetailDTO().getCustomerName()).orElse(""));

                //客户型号
                Cell cell4 = dataRow.createCell(creditNum++);
                cell4.setCellStyle(cellStyle);
                cell4.setCellValue(Optional.ofNullable(data.getRegisterDetailDTO().getCustomerModel()).orElse(""));

                //订单数量
                Cell cell5 = dataRow.createCell(creditNum++);
                cell5.setCellStyle(cellStyle);
                cell5.setCellValue(Optional.ofNullable(data.getOrderNum()).orElse(""));

                //客户订单号
                Cell cell6 = dataRow.createCell(creditNum++);
                cell6.setCellStyle(cellStyle);
                cell6.setCellValue(Optional.ofNullable(data.getCustomerOrderCode()).orElse(""));

                //平方数
                Cell cell7 = dataRow.createCell(creditNum++);
                cell7.setCellStyle(cellStyle);
                cell7.setCellValue(Optional.ofNullable(data.getSquareNum()).orElse(""));

                //已完成数量
                Cell cell8 = dataRow.createCell(creditNum++);
                cell8.setCellStyle(cellStyle);
                cell8.setCellValue(Optional.ofNullable(data.getCompletedNum()).orElse(""));

                //未完成数量
                Cell cell9 = dataRow.createCell(creditNum++);
                cell9.setCellStyle(cellStyle);
                cell9.setCellValue(Optional.ofNullable(data.getUncompletedNum()).orElse(""));

                //下单日期
                Cell cell10 = dataRow.createCell(creditNum++);
                cell10.setCellStyle(cellStyle);
                cell10.setCellValue(Optional.ofNullable(data.getOrderDate()).orElse(""));

                //交货日期
                Cell cell11 = dataRow.createCell(creditNum++);
                cell11.setCellStyle(cellStyle);
                cell11.setCellValue(Optional.ofNullable(data.getDeliveryDate()).orElse(""));

                //备注
                Cell cell12 = dataRow.createCell(creditNum++);
                cell11.setCellStyle(cellStyle);
                cell11.setCellValue(Optional.ofNullable(data.getSurplusRemarks()).orElse(""));

                rowCount++;
                totalCount++;
            }
            break;

        }
    }
}

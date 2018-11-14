package com.dongyu.company.common.view;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

/**
 * excel设置
 *
 * @author TYF
 * @date 2018/11/13
 * @since 1.0.0
 */
public abstract class ExcelView extends AbstractXlsxStreamingView {

    protected abstract void setRow(Workbook workbook, Map<String, Object> map);

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fielName = String.valueOf(map.get("fileName"));
        String userAgent = request.getHeader("user-agent");

        fielName = URLEncoder.encode(fielName, "UTF8");
        if (userAgent.contains("Firefox")) {
            response.setCharacterEncoding("utf-8");
            fielName = new String(fielName.getBytes(), "ISO-8859-1");
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + fielName);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.ms-excel");
        setRow(workbook, map);
    }
}

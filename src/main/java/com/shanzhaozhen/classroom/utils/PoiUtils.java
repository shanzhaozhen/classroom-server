package com.shanzhaozhen.classroom.utils;

import com.shanzhaozhen.classroom.param.ExcelParam;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

public class PoiUtils {

    public static Workbook writeExcel(ExcelParam excelParam) {

        String sheetName = excelParam.getSheetName();
        String title = excelParam.getTitle();
        List<String> rowName = excelParam.getRowName();
        List<List<Object>> dataList = excelParam.getDataList();
        String footer = excelParam.getFooter();

        Workbook workbook;
        Sheet sheet;

        workbook = new HSSFWorkbook();

        if (StringUtils.isEmpty(sheetName)) {
            sheetName = "Sheet1";
        }
        sheet = workbook.createSheet(sheetName);

        int index = 0;

        /**
         * 设置标题
         */
        if (!StringUtils.isEmpty(title)) {
            Row titleRow = sheet.createRow(index);
            Cell cell = titleRow.createCell(0, CellType.STRING);
            cell.setCellValue(title);
            sheet.addMergedRegion(new CellRangeAddress(0,0,0, rowName.size() - 1));
            index++;
        }

        /**
         * 设置表头
         */
        if (rowName != null) {
            Row headerRow = sheet.createRow(index);
            for (int i = 0; i < rowName.size(); i++) {
                Cell cell = headerRow.createCell(i, CellType.STRING);
                cell.setCellValue(rowName.get(i));
            }
            index++;
        }


        /**
         * 设置表的内容
         */
        for (int i = 0; i < dataList.size(); i++) {
            List<Object> item = dataList.get(i);
            Row content = sheet.createRow(i + index);
            for (int j = 0; j < item.size(); j++) {
                Object data = item.get(j);
                Cell cell = content.createCell(j);
                if (data instanceof Integer) {
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue((Integer) data);
                } else if (data instanceof Float) {
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue((Float) data);
                } else if (data instanceof Double) {
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue((Double) data);
                } else if (data instanceof Date) {
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue((Date) data);
                } else if (data instanceof String) {
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue((String) data);
                } else if (data == null) {
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue("");
                } else {
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.toString());
                }
            }
        }
        if (!StringUtils.isEmpty(footer)) {
            Row titleRow = sheet.createRow(dataList.size() + index);
            Cell cell = titleRow.createCell(0, CellType.STRING);
            cell.setCellValue(footer);
            sheet.addMergedRegion(new CellRangeAddress(dataList.size() + index,dataList.size() + index,0, rowName.size() - 1));
        }
        return workbook;
    }

    public static void exportExcel(HttpServletResponse response, Workbook workbook) {

        String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";

        // 下载文件的默认名称
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

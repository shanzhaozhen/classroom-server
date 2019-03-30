package com.shanzhaozhen.classroom.param;

import java.util.List;


public class ExcelParam {

    //工作簿名称
    private String sheetName;

    //显示的导出表的标题
    private String title;

    //导出表的列名
    private List<String> rowName ;

    private List<List<Object>> dataList;

    private String footer;

    //构造方法，传入要导出的数据
    public ExcelParam(String sheetName, String title, List<String> rowName, List<List<Object>> dataList, String footer) {
        this.sheetName = sheetName;
        this.title = title;
        this.rowName = rowName;
        this.dataList = dataList;
        this.footer = footer;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getRowName() {
        return rowName;
    }

    public void setRowName(List<String> rowName) {
        this.rowName = rowName;
    }

    public List<List<Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<List<Object>> dataList) {
        this.dataList = dataList;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }
}

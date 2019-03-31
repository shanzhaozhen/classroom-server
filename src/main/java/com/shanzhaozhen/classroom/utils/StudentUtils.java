package com.shanzhaozhen.classroom.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class StudentUtils {

    /**
     * 计算百分率返回字符串类型
     */
    public static String calPercent(int number, int totalNumber) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
        //可以设置精确几位小数
        decimalFormat.setMaximumFractionDigits(2);
        //模式 例如四舍五入
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num = number * 1.0 / totalNumber * 100;
        return decimalFormat.format(accuracy_num) + "%";
    }



}

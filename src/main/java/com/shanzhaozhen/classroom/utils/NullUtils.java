package com.shanzhaozhen.classroom.utils;

import java.util.Collections;
import java.util.List;

public class NullUtils {

    /**
     * 判断字符串是否为空或者为空串
     * @param string
     * @return
     */
    public static boolean isNull(String string) {
        if (string == null || string.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否不为空或者为空串
     * @param string
     * @return
     */
    public static boolean isNotNull(String string) {
        if (string == null || string.equals("")) {
            return false;
        }
        return true;
    }


    /**
     * 由于List的元素可以为空，如果有需要可以将为空的元素清除
     * @param list
     * @return
     */
    public static List clearNull(List list) {
        list.removeAll(Collections.singleton(null));
        return list;
    }

}

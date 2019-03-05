package com.shanzhaozhen.classroom.utils;

import com.shanzhaozhen.classroom.bean.SysPermission;

import java.util.Comparator;

public class ComparatorUtils implements Comparator<SysPermission> {

    @Override
    public int compare(SysPermission o1, SysPermission o2) {
        if(o1.getPriority() == null || o2.getPriority() == null) {
            return 0;
        }
        if (o1.getPriority() > o2.getPriority()) {
            return 1;
        } else if (o1.getPriority() < o2.getPriority()) {
            return -1;
        } else {
            return 0;
        }
    }

}

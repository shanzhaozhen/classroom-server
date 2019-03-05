package com.shanzhaozhen.classroom.admin.service;

import com.shanzhaozhen.classroom.bean.SysPermission;

import java.util.List;
import java.util.Map;

public interface SysPermissionService {

    List<SysPermission> getMenu();

    Map<String, Object> getAllMenu();

}

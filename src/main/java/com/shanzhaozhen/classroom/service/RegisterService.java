package com.shanzhaozhen.classroom.service;

import com.shanzhaozhen.classroom.bean.SysUser;

import java.util.Map;

public interface RegisterService {

    Map<String, Object> RegisterNewUser(SysUser sysUser);

    Map<String, Boolean> checkUsername(String username);

}

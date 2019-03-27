package com.shanzhaozhen.classroom.admin.service;

import com.shanzhaozhen.classroom.bean.SysUser;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface SysUserService {

    Map<String, Object> getUserInfo(HttpServletRequest httpServletRequest);

    SysUser getSysUserByUsername(String username);

    SysUser getSysUserByOpenId(String openId);

    Map<String, Object> saveSysUser(SysUser sysUser);

    Map<String, Object> getFaceToken(MultipartFile multipartFile);

    Map<String, Object> binding(String code);

    Map<String, Object> unbinding();
}

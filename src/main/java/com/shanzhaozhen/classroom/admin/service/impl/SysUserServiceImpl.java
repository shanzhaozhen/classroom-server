package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.repository.SysUserRepository;
import com.shanzhaozhen.classroom.admin.service.SysUserService;
import com.shanzhaozhen.classroom.bean.SysRole;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.SysUserInfo;
import com.shanzhaozhen.classroom.config.MyJwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private MyJwtTokenProvider myJwtTokenProvider;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public Map<String, Object> getUserInfo(HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>();

        String jwtToken = myJwtTokenProvider.getJwtTokenFromRequest(httpServletRequest);

        if (StringUtils.isEmpty(jwtToken)) {
            map.put("success", false);
            map.put("message", "识别不到当前账号的登陆信息，请重新登陆");
            return map;
        }
        String username = myJwtTokenProvider.getUsername(jwtToken);

        SysUser sysUser = sysUserRepository.findSysUserByUsername(username);

        if (sysUser == null) {
            map.put("success", false);
            map.put("message", "没有找到当前的用户信息");
            return map;
        }

        SysUserInfo sysUserInfo = sysUser.getSysUserInfo();

        Set<SysRole> roleList = sysUser.getSysRoles();

        List<String> roles = new ArrayList<>();

        for (SysRole sysRole : roleList) {
            roles.add(sysRole.getName());
        }

        map.put("success", true);
        map.put("username", sysUser.getUsername());
        map.put("fullName", sysUserInfo.getFullName());
        map.put("nickName", sysUserInfo.getNickName());
        map.put("avatarUrl", sysUserInfo.getAvatarUrl());
        map.put("roles", roles);

        return map;
    }

    @Override
    public SysUser getSysUserByUsername(String username) {
        return sysUserRepository.findSysUserByUsername(username);
    }

    @Override
    public SysUser getSysUserByOpenId(String openId) {
        return sysUserRepository.findSysUserByOpenId(openId);
    }

    @Override
    @Transactional
    public Map<String, Object> saveSysUser(SysUser sysUser) {
        sysUserRepository.save(sysUser);
        return null;
    }

}

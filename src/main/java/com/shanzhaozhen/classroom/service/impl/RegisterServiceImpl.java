package com.shanzhaozhen.classroom.service.impl;

import com.shanzhaozhen.classroom.admin.repository.SysUserInfoRepository;
import com.shanzhaozhen.classroom.admin.repository.SysUserRepository;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.SysUserInfo;
import com.shanzhaozhen.classroom.service.RegisterService;
import com.shanzhaozhen.classroom.utils.MessageUtils;
import com.shanzhaozhen.classroom.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    @Transactional
    public Map<String, Object> RegisterNewUser(SysUser sysUser) {
        if (StringUtils.isEmpty(sysUser.getUsername())) {
            return MessageUtils.resultFailureMessage("填写的用户名有误！");
        }
        if (StringUtils.isEmpty(sysUser.getPassword())) {
            return MessageUtils.resultFailureMessage("填写的密码有误！");
        }
        int count = sysUserRepository.countByUsername(sysUser.getUsername());
        if (count > 0) {
            return MessageUtils.resultFailureMessage("注册失败，用户名已存在！");
        }
        SysUser newUser = new SysUser();
        newUser.setUsername(sysUser.getUsername());
        newUser.setPassword(PasswordUtils.encryption(sysUser.getPassword()));
        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setCredentialsNonExpired(true);
        newUser.setEnabled(true);
        newUser.setSysUserInfo(new SysUserInfo());
        sysUserRepository.save(newUser);
        return MessageUtils.resultSuccessMessage("注册成功，等待管理员通过审核！");
    }

    @Override
    public Map<String, Boolean> checkUsername(String username) {
        int count = sysUserRepository.countByUsername(username);
        Map<String, Boolean> map = new HashMap<>();
        if (count > 0) {
            map.put("valid", false);
        } else {
            map.put("valid", true);
        }
        return map;
    }

}

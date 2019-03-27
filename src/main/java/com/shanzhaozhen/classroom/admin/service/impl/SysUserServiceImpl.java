package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.repository.SysUserRepository;
import com.shanzhaozhen.classroom.admin.service.SysUserService;
import com.shanzhaozhen.classroom.bean.SysRole;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.SysUserInfo;
import com.shanzhaozhen.classroom.config.MyJwtTokenProvider;
import com.shanzhaozhen.classroom.utils.FaceServiceProvider;
import com.shanzhaozhen.classroom.utils.WechatServiceProvider;
import com.shanzhaozhen.classroom.utils.UserDetailsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private MyJwtTokenProvider myJwtTokenProvider;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private WechatServiceProvider wechatServiceProvider;

    @Autowired
    private FaceServiceProvider faceServiceProvider;

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
        map.put("number", sysUserInfo.getNumber());
        map.put("fullName", sysUserInfo.getFullName());
        map.put("nickName", sysUserInfo.getNickName());
        map.put("avatarUrl", sysUserInfo.getAvatarUrl());
        map.put("openId", sysUser.getOpenId());
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

    @Override
    public Map<String, Object> getFaceToken(MultipartFile multipartFile) {
        Map<String, Object> map = new HashMap<>();
        if (multipartFile == null) {
            map.put("success", false);
            map.put("msg", "没有接受到你的照片");
            return map;
        }
        String faceToken = faceServiceProvider.getFaceToken(multipartFile);
        if (StringUtils.isEmpty(faceToken)) {
            map.put("success", false);
            map.put("msg", "脸谱生成失败");
            return map;
        }
        map.put("success", true);
        map.put("msg", "脸谱生成成功");
        map.put("faceToken", "faceToken");
        return map;
    }

    @Override
    public Map<String, Object> binding(String code) {

        Map<String, Object> map = new HashMap<>();

        String username = UserDetailsUtils.getUsername();

        SysUser sysUser = sysUserRepository.findSysUserByUsername(username);

        if (sysUser == null) {
            map.put("success", false);
            map.put("message", "没有找到当前的用户信息");
            return map;
        }

        map = wechatServiceProvider.getMpOpenId(code);
        if ((boolean) map.get("success") != true) {
            return map;
        }
        String openId = (String) map.get("openId");
        sysUser.setOpenId(openId);
        sysUserRepository.save(sysUser);

        map = new HashMap<>();
        map.put("success", true);
        map.put("msg", "绑定微信成功");

        return map;
    }

    @Override
    public Map<String, Object> unbinding() {

        Map<String, Object> map = new HashMap<>();

        String username = UserDetailsUtils.getUsername();

        SysUser sysUser = sysUserRepository.findSysUserByUsername(username);

        if (sysUser == null) {
            map.put("success", false);
            map.put("message", "没有找到当前的用户信息");
            return map;
        }

        sysUser.setOpenId("");
        sysUserRepository.save(sysUser);

        map = new HashMap<>();
        map.put("success", true);
        map.put("msg", "解绑微信成功");

        return map;
    }

}

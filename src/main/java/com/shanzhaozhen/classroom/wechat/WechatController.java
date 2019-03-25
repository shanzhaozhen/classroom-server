package com.shanzhaozhen.classroom.wechat;

import com.shanzhaozhen.classroom.admin.service.SysUserService;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.SysUserInfo;
import com.shanzhaozhen.classroom.config.MyJwtTokenProvider;
import com.shanzhaozhen.classroom.utils.WechatServiceProvider;
import com.shanzhaozhen.classroom.utils.UserDetailsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private WechatServiceProvider wechatServiceProvider;

    @Autowired
    private MyJwtTokenProvider myJwtTokenProvider;

    @Autowired
    private SysUserService sysUserService;


    @PostMapping("/login")
    public Map<String, Object> wechatLogin(@RequestBody String code) {
        Map<String, Object> map;

        map = wechatServiceProvider.getMpOpenId(code);

        if ((boolean) map.get("success") == true) {
            String openId = (String) map.get("openId");
            SysUser sysUser = sysUserService.getSysUserByOpenId(openId);
            if (sysUser == null) {
                map = new HashMap<>();
                map.put("success", false);
                map.put("msg", "该微信未注册");
                return map;
            } else {
                List<String> roles = new ArrayList<>();
                roles.add("ROLE_ADMIN");

                String token = myJwtTokenProvider.createToken(sysUser.getUsername(), roles, true);
                // 登陆成功返回
                map = new HashMap<>();
                map.put("success", true);
                map.put("access-token", token);
                return map;
            }

        } else {
            return map;
        }
    }

    @PutMapping("/update/userinfo")
    public Map<String, Object> updateUser(@RequestBody SysUserInfo sysUserInfo) {
        Map<String, Object> map = new HashMap<>();
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            map.put("success", false);
            map.put("msg", "用户不存在");
            return map;
        }

        SysUserInfo tempUserInfo = sysUser.getSysUserInfo();

        if (tempUserInfo == null) {
            sysUser.setSysUserInfo(sysUserInfo);
            sysUserService.saveSysUser(sysUser);
        } else {
            BeanUtils.copyProperties(sysUserInfo, tempUserInfo, "id", "fullName", "birthday", "email",
                    "phoneNumber", "address", "introduction", "createdDate", "lastModifiedDate");
            sysUser.setSysUserInfo(tempUserInfo);
            sysUserService.saveSysUser(sysUser);
        }

        map.put("success", true);
        map.put("msg", "信息更新成功");
        return map;
    }

}

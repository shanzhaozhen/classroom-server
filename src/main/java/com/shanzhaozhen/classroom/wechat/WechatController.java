package com.shanzhaozhen.classroom.wechat;

import com.shanzhaozhen.classroom.admin.service.SysUserService;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.config.MyJwtTokenProvider;
import com.shanzhaozhen.classroom.config.WechatServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/openid")
    public Map<String, Object> getMpOpenId(String code) {

        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isEmpty(code)) {
            map.put("success", false);
            map.put("msg", "缺少code");
            return map;
        }

        String openId = wechatServiceProvider.getOpenIdByCode2SessionAndCode(code);

        if (StringUtils.isEmpty(openId)) {
            map.put("success", false);
            map.put("msg", "获取openId失败");
            return map;
        }

        map.put("success", true);
        map.put("msg", "openId获取成功");
        map.put("openId", openId);
        return map;
    }

    @GetMapping("/login")
    public Map<String, Object> wechatLogin(String code) {
        Map<String, Object> map;

        map = this.getMpOpenId(code);

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
                map.put("token", token);
                return map;
            }

        } else {
            return map;
        }

    }

}

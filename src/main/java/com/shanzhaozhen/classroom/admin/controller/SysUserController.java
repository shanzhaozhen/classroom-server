package com.shanzhaozhen.classroom.admin.controller;

import com.shanzhaozhen.classroom.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/admin/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("info")
    public Map<String, Object> getUserInfo(HttpServletRequest httpServletRequest) {
        return sysUserService.getUserInfo(httpServletRequest);
    }


    @PutMapping("binding")
    public Map<String, Object> binding(@RequestBody String code) {
        return sysUserService.binding(code);
    }

    @PutMapping("unbinding")
    public Map<String, Object> unbinding() {
        return sysUserService.unbinding();
    }

}

package com.shanzhaozhen.classroom.controller;

import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    @ResponseBody
    public Map<String, Object> register(@RequestBody SysUser sysUser) {
        return registerService.RegisterNewUser(sysUser);
    }

    @GetMapping("/{username}")
    @ResponseBody
    public Map<String, Boolean> checkUserName(@PathVariable("username") String username) {
        return registerService.checkUsername(username);
    }


}

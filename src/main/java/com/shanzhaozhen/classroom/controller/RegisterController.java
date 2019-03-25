package com.shanzhaozhen.classroom.controller;

import com.alibaba.fastjson.JSONObject;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public Map<String, Object> register(@RequestBody JSONObject jsonParam) {
        return registerService.RegisterNewUser(jsonParam);
    }

    @GetMapping("/{username}")
    public Map<String, Boolean> checkUserName(@PathVariable("username") String username) {
        return registerService.checkUsername(username);
    }


}

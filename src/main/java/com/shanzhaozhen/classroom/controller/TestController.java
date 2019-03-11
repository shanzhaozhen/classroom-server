package com.shanzhaozhen.classroom.controller;

import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @GetMapping("admin/abc")
    @ResponseBody
    public String test1() {

        return "test";
    }


    @GetMapping("admin/bcd")
    @ResponseBody
    public String test2() {

        return "test";
    }

    @GetMapping("bcd")
    @ResponseBody
    public String test4() {
        throw new JwtException("dd");
//        return "test";
    }




}

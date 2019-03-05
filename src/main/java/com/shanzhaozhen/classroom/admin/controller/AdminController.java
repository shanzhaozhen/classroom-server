package com.shanzhaozhen.classroom.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/index")
    public String index(Model model) {
        return "admin/index";
    }

    @GetMapping("/a")
    @ResponseBody
    public String a() {
        return "a";
    }


    @GetMapping("/b")
    @ResponseBody
    public String b() {
        return "b";
    }

}

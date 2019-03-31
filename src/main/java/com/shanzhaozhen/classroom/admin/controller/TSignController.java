package com.shanzhaozhen.classroom.admin.controller;

import com.shanzhaozhen.classroom.admin.service.TSignService;
import com.shanzhaozhen.classroom.bean.TSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class TSignController {

    @Autowired
    private TSignService tSignService;

    @GetMapping("/sign-task/{signTaskId}/sign")
    public Page<TSign> getTSignPage(@PathVariable("signTaskId") Integer signTaskId, String keyword, Pageable pageable) {
        Page<TSign> page = tSignService.getTSignPage(signTaskId, keyword, pageable);
        return page;
    }

    @PostMapping("/sign")
    public Map<String, Object> signWitFace(@RequestBody TSign tSign) {
        return tSignService.sign(tSign);
    }

    @GetMapping("/sign-task/{signTaskId}/sign/my")
    public Map<String, Object> getSignDetail(@PathVariable("signTaskId") Integer signTaskId) {
        return tSignService.getTSignBySignTaskId(signTaskId);
    }

    @GetMapping("/sign-task/{signTaskId}/sign/export")
    public void exportSignDataBySignTaskId(@PathVariable("signTaskId") Integer signTaskId, HttpServletResponse httpServletResponse) {
        tSignService.exportSignDataBySignTaskId(signTaskId, httpServletResponse);
    }

}

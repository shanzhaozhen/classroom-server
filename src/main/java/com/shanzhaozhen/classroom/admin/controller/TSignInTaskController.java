package com.shanzhaozhen.classroom.admin.controller;

import com.shanzhaozhen.classroom.admin.service.TSignInTaskService;
import com.shanzhaozhen.classroom.bean.TSignInTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class TSignInTaskController {

    @Autowired
    private TSignInTaskService tSignInTaskService;

    @GetMapping("/signintask")
    public Page<TSignInTask> getTSignInTaskPage(Integer classId, String keyword, Pageable pageable) {
        Page<TSignInTask> page = tSignInTaskService.getTSignInTaskPage(classId, keyword, pageable);
        return page;
    }

    @PostMapping("/signintask")
    public Map<String, Object> createTSignInTask(@RequestBody TSignInTask tSignInTask) {
        return tSignInTaskService.createTSignInTask(tSignInTask);
    }

    @PutMapping("/signintask")
    public Map<String, Object> updateTSignInTask(@RequestBody TSignInTask tSignInTask) {
        return tSignInTaskService.updateTSignInTask(tSignInTask);
    }

    @DeleteMapping("/signintask/{id}")
    public Map<String, Object> deleteTSignInTask(@PathVariable("id") Integer id) {
        return tSignInTaskService.deleteTSignInTask(id);
    }

}

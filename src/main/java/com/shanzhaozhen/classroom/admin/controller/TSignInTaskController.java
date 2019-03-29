package com.shanzhaozhen.classroom.admin.controller;

import com.shanzhaozhen.classroom.admin.service.TSignInTaskService;
import com.shanzhaozhen.classroom.bean.TSignInTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/signintask/{id}")
    public TSignInTask getTSignInTask(@PathVariable("id") Integer id) {
        return tSignInTaskService.getTSignInTaskById(id);
    }

    @GetMapping("/signintask/classroom/{id}")
    public List<TSignInTask> getSignInTaskListByClassId(@PathVariable("id") Integer classId) {
        return tSignInTaskService.getSignInTaskListByClassId(classId);
    }

    @GetMapping("/signintask/attendance/{id}")
    public Map<String, Object> getAttendanceBySignInTaskId(@PathVariable("id") Integer signInTaskId) {
        return tSignInTaskService.getAttendanceRateBySignInTaskId(signInTaskId);
    }

}

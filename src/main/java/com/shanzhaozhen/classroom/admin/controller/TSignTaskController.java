package com.shanzhaozhen.classroom.admin.controller;

import com.shanzhaozhen.classroom.admin.service.TSignTaskService;
import com.shanzhaozhen.classroom.bean.TSignTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class TSignTaskController {

    @Autowired
    private TSignTaskService tSignTaskService;

    @GetMapping("/sign-task")
    public Page<TSignTask> getTSignTaskPage(Integer classroomId, String keyword, Pageable pageable) {
        Page<TSignTask> page = tSignTaskService.getTSignTaskPage(classroomId, keyword, pageable);
        return page;
    }

    @PostMapping("/sign-task")
    public Map<String, Object> createTSignTask(@RequestBody TSignTask tSignTask) {
        return tSignTaskService.createTSignTask(tSignTask);
    }

    @PutMapping("/sign-task")
    public Map<String, Object> updateTSignTask(@RequestBody TSignTask tSignTask) {
        return tSignTaskService.updateTSignTask(tSignTask);
    }

    @DeleteMapping("/sign-task/{id}")
    public Map<String, Object> deleteTSignTask(@PathVariable("id") Integer id) {
        return tSignTaskService.deleteTSignTask(id);
    }

    @GetMapping("/sign-task/{id}")
    public TSignTask getTSignTask(@PathVariable("id") Integer id) {
        return tSignTaskService.getTSignTaskById(id);
    }

    @GetMapping("/classroom/{classroomId}/sign-task")
    public List<TSignTask> getSignTaskListByClassroomId(@PathVariable("classroomId") Integer classroomId) {
        return tSignTaskService.getSignTaskListByClassroomId(classroomId);
    }

    @GetMapping("/sign-task/{signTaskId}/attendance")
    public Map<String, Object> getAttendanceBySignTaskId(@PathVariable("signTaskId") Integer signTaskId) {
        return tSignTaskService.getAttendanceRateBySignTaskId(signTaskId);
    }

}

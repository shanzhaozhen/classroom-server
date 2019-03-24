package com.shanzhaozhen.classroom.admin.controller;

import com.shanzhaozhen.classroom.admin.service.THomeworkTaskService;
import com.shanzhaozhen.classroom.admin.service.TSignInTaskService;
import com.shanzhaozhen.classroom.bean.THomeworkTask;
import com.shanzhaozhen.classroom.bean.TSignInTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class THomeworkTaskController {

    @Autowired
    private THomeworkTaskService tHomeworkTaskService;

    @GetMapping("/homeworktask")
    public Page<THomeworkTask> getTHomeworkTaskPage(Integer classId, String keyword, Pageable pageable) {
        Page<THomeworkTask> page = tHomeworkTaskService.getTHomeworkTaskPage(classId, keyword, pageable);
        return page;
    }

    @PostMapping("/homeworktask")
    public Map<String, Object> createTHomeworkTask(@RequestBody THomeworkTask tHomeworkTask) {
        return tHomeworkTaskService.createTHomeworkTask(tHomeworkTask);
    }

    @PutMapping("/homeworktask")
    public Map<String, Object> updateTHomeworkTask(@RequestBody THomeworkTask tHomeworkTask) {
        return tHomeworkTaskService.updateTHomeworkTask(tHomeworkTask);
    }

    @DeleteMapping("/homeworktask/{id}")
    public Map<String, Object> deleteTHomeworkTask(@PathVariable("id") Integer id) {
        return tHomeworkTaskService.deleteTHomeworkTask(id);
    }

    @GetMapping("/homeworktask/{id}")
    public THomeworkTask getTHomeworkTask(@PathVariable("id") Integer id) {
        return tHomeworkTaskService.getTHomeworkTaskById(id);
    }

    @GetMapping("/homeworktask/classroom/{id}")
    public List<THomeworkTask> getHomeworkTaskListByClassId(@PathVariable("id") Integer classId) {
        return tHomeworkTaskService.getHomeworkTaskListByClassId(classId);
    }

}

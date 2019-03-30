package com.shanzhaozhen.classroom.admin.controller;

import com.shanzhaozhen.classroom.admin.service.THomeworkTaskService;
import com.shanzhaozhen.classroom.bean.THomeworkTask;
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

    @GetMapping("/homework-task")
    public Page<THomeworkTask> getTHomeworkTaskPage(Integer classId, String keyword, Pageable pageable) {
        Page<THomeworkTask> page = tHomeworkTaskService.getTHomeworkTaskPage(classId, keyword, pageable);
        return page;
    }

    @PostMapping("/homework-task")
    public Map<String, Object> createTHomeworkTask(@RequestBody THomeworkTask tHomeworkTask) {
        return tHomeworkTaskService.createTHomeworkTask(tHomeworkTask);
    }

    @PutMapping("/homework-task")
    public Map<String, Object> updateTHomeworkTask(@RequestBody THomeworkTask tHomeworkTask) {
        return tHomeworkTaskService.updateTHomeworkTask(tHomeworkTask);
    }

    @DeleteMapping("/homework-task/{id}")
    public Map<String, Object> deleteTHomeworkTask(@PathVariable("id") Integer id) {
        return tHomeworkTaskService.deleteTHomeworkTask(id);
    }

    @GetMapping("/homework-task/{id}")
    public THomeworkTask getTHomeworkTask(@PathVariable("id") Integer id) {
        return tHomeworkTaskService.getTHomeworkTaskById(id);
    }

    @GetMapping("/homework-task/classroom/{id}")
    public List<THomeworkTask> getHomeworkTaskListByClassId(@PathVariable("id") Integer classId) {
        return tHomeworkTaskService.getHomeworkTaskListByClassId(classId);
    }

    @GetMapping("/homeworktask/submitrate/{homeworkTaskId}")
    public Map<String, Object> getSubmitRateByHomeworkTaskId(@PathVariable("homeworkTaskId") Integer homeworkTaskId) {
        return tHomeworkTaskService.getSubmitRateByHomeworkTaskId(homeworkTaskId);
    }

}

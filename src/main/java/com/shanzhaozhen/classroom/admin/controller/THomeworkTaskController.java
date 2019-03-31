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
    public Page<THomeworkTask> getTHomeworkTaskPage(Integer classroomId, String keyword, Pageable pageable) {
        Page<THomeworkTask> page = tHomeworkTaskService.getTHomeworkTaskPage(classroomId, keyword, pageable);
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

    @GetMapping("/classroom/{classroomId}/homework-task")
    public List<THomeworkTask> getHomeworkTaskListByClassroomId(@PathVariable("classroomId") Integer classroomId) {
        return tHomeworkTaskService.getHomeworkTaskListByClassroomId(classroomId);
    }

    @GetMapping("/homework-task/{homeworkTaskId}/commit")
    public Map<String, Object> getSubmitRateByHomeworkTaskId(@PathVariable("homeworkTaskId") Integer homeworkTaskId) {
        return tHomeworkTaskService.getSubmitRateByHomeworkTaskId(homeworkTaskId);
    }

}

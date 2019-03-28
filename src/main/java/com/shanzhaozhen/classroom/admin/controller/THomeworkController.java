package com.shanzhaozhen.classroom.admin.controller;

import com.shanzhaozhen.classroom.admin.service.THomeworkService;
import com.shanzhaozhen.classroom.bean.THomework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class THomeworkController {

    @Autowired
    private THomeworkService tHomeworkService;

    @GetMapping("/homeworks/{homeworkTaskId}")
    public Page<THomework> getTSignInPage(@PathVariable("homeworkTaskId") Integer homeworkTaskId, String keyword, Pageable pageable) {
        Page<THomework> page = tHomeworkService.getTHomeworkPage(homeworkTaskId, keyword, pageable);
        return page;
    }

    @PutMapping("/homework/giveScore/{homeworkId}")
    public Map<String, Object> giveHomeworkScore(@PathVariable("homeworkId") Integer homeworkId, Integer score) {
        return tHomeworkService.giveHomeworkScore(homeworkId, score);
    }

    @GetMapping("/homework/{id}")
    public Map<String, Object> getTHomework(@PathVariable("id") Integer id) {
        return tHomeworkService.getTHomework(id);
    }

    @PostMapping("/homework")
    public Map<String, Object> saveTHomework(@RequestBody THomework tHomework) {
        return tHomeworkService.saveTHomework(tHomework);
    }

    @GetMapping("/homework/taskid/{homeworkTaskId}")
    public Map<String, Object> getSignInDetail(@PathVariable("homeworkTaskId") Integer homeworkTaskId) {
        return tHomeworkService.getTHomeworkBySignInTaskId(homeworkTaskId);
    }

}

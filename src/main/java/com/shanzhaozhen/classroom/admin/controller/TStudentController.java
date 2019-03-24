package com.shanzhaozhen.classroom.admin.controller;

import com.shanzhaozhen.classroom.admin.service.TStudentService;
import com.shanzhaozhen.classroom.bean.TStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class TStudentController {

    @Autowired
    private TStudentService tStudentService;

    @GetMapping("/students/{classId}")
    public Page<TStudent> getTStudentPage(@PathVariable("classId") Integer classId, String keyword, Pageable pageable) {
        Page<TStudent> page = tStudentService.getTStudentPage(classId, keyword, pageable);
        return page;
    }

    @PostMapping("/student/join/{id}")
    public Map<String, Object> joinClass(@PathVariable("id") Integer classId) {
        return tStudentService.joinClass(classId);
    }

    @DeleteMapping("/student/{id}")
    public Map<String, Object> moveOutOfClass(@PathVariable("id") Integer id) {
        return tStudentService.moveOutOfClass(id);
    }

//    @DeleteMapping("/student/{classId}")
//    public Map<String, Object> leaveClass(@PathVariable("classId") Integer classId, Integer studentId) {
//        return tStudentService.leaveClass(classId, studentId);
//    }

}

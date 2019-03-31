package com.shanzhaozhen.classroom.admin.controller;

import com.shanzhaozhen.classroom.admin.service.TStudentService;
import com.shanzhaozhen.classroom.bean.TStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class TStudentController {

    @Autowired
    private TStudentService tStudentService;

    @GetMapping("/classroom/{classroomId}/student")
    public Page<TStudent> getTStudentPage(@PathVariable("classroomId") Integer classroomId, String keyword, Pageable pageable) {
        Page<TStudent> page = tStudentService.getTStudentPage(classroomId, keyword, pageable);
        return page;
    }

    @PostMapping("/student")
    public Map<String, Object> addTStudent(@RequestBody TStudent tStudent) {
        return tStudentService.addTStudent(tStudent);
    }

    @DeleteMapping("/student/{id}")
    public Map<String, Object> delTStudent(@PathVariable("id") Integer id) {
        return tStudentService.delTStudent(id);
    }

    @GetMapping("/classroom/{classroomId}/student/export")
    public void exportStudentDataByClassroomId(@PathVariable("classroomId") Integer classroomId, HttpServletResponse httpServletResponse) {
        tStudentService.exportStudentDataByClassroomId(classroomId, httpServletResponse);
    }


}

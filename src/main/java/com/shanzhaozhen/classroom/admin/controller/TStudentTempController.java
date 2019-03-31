package com.shanzhaozhen.classroom.admin.controller;

import com.shanzhaozhen.classroom.admin.service.TStudentTempService;
import com.shanzhaozhen.classroom.bean.TStudentTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/admin")
public class TStudentTempController {

    @Autowired
    private TStudentTempService tStudentTempService;

    @GetMapping("/student-temp")
    public Page<TStudentTemp> getTStudentPage(Pageable pageable) {
        return tStudentTempService.getTStudentTempPage(pageable);
    }

    @PostMapping("/student-temp")
    public Map<String, Object> addTStudent(@RequestBody TStudentTemp tStudentTemp) {
        return tStudentTempService.addTStudentTemp(tStudentTemp);
    }

    @PutMapping("/student-temp/{id}/pass")
    public Map<String, Object> passStudent(@PathVariable("id") Integer id) {
        return tStudentTempService.passStudent(id);
    }

    @DeleteMapping("/student-temp/{id}")
    public Map<String, Object> refuseStudent(@PathVariable("id") Integer id) {
        return tStudentTempService.deleteTStudentTemp(id);
    }

}

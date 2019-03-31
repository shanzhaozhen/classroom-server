package com.shanzhaozhen.classroom.admin.service;

import com.shanzhaozhen.classroom.bean.TStudentTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface TStudentTempService {

    Page<TStudentTemp> getTStudentTempPage(Pageable pageable);

    TStudentTemp findTStudentTempById(Integer id);

    TStudentTemp findTStudentTempByClassroomIdAndStudentId(Integer classroomId, Integer studentId);

    Map<String, Object> addTStudentTemp(TStudentTemp tStudentTemp);

    Map<String, Object> passStudent(Integer id);

    Map<String, Object> deleteTStudentTemp(Integer id);
}

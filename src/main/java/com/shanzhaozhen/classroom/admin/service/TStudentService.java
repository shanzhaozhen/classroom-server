package com.shanzhaozhen.classroom.admin.service;

import com.shanzhaozhen.classroom.bean.TStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface TStudentService {

    Page<TStudent> getTStudentPage(Integer classId, String keyword, Pageable pageable);

    Map<String, Object> joinClass(Integer classId);

    Map<String, Object> moveOutOfClass(Integer id);

    Map<String, Object> leaveClass(Integer classId, Integer studentId);

    void removeAllStudentByClassId(Integer classId);
}

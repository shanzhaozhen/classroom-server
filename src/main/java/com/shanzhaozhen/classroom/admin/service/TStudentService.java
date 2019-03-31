package com.shanzhaozhen.classroom.admin.service;

import com.shanzhaozhen.classroom.bean.TStudent;
import com.shanzhaozhen.classroom.bean.TStudentTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface TStudentService {

    Page<TStudent> getTStudentPage(Integer classroomId, String keyword, Pageable pageable);

    void getStudentOtherInfo(Integer classroomId, List<TStudent> list);

    Map<String, Object> addTStudent(TStudent tStudent);

    Map<String, Object> delTStudent(Integer id);

    Map<String, Object> leaveClass(Integer classroomId, Integer studentId);

    void removeAllStudentByClassroomId(Integer classroomId);

    void exportStudentDataByClassroomId(Integer classroomId, HttpServletResponse httpServletResponse);

    TStudent findTStudentByClassroomIdAndStudentId(Integer classroomId, Integer studentId);

    void savePassStudent(TStudentTemp tStudentTemp);
}

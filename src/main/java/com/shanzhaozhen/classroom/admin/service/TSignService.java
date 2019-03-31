package com.shanzhaozhen.classroom.admin.service;

import com.shanzhaozhen.classroom.bean.TSign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface TSignService {

    Page<TSign> getTSignPage(Integer signTaskId, String keyword, Pageable pageable);

    Map<String, Object> sign(TSign tSign);

    Map<String, Object> getTSignBySignTaskId(Integer signTaskId);

    void exportSignDataBySignTaskId(Integer signTaskId, HttpServletResponse httpServletResponse);

    int countTSignsByStudentIdAndClassroomId(Integer studentId, Integer classroomId);
}

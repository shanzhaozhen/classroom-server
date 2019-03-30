package com.shanzhaozhen.classroom.admin.service;

import com.shanzhaozhen.classroom.bean.THomework;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface THomeworkService {

    Page<THomework> getTHomeworkPage(Integer homeworkTaskId, String keyword, Pageable pageable);

    Map<String, Object> giveHomeworkScore(Integer homeworkId, Integer score);

    Map<String, Object> saveTHomework(THomework tHomework);

    Map<String, Object> getTHomework(Integer id);

    Map<String, Object> getTHomeworkByHomeworkTaskId(Integer homeworkTaskId);

    void exporHomeworkDataByHomeworkTaskId(Integer homeworkTaskId, HttpServletResponse httpServletResponse);
}

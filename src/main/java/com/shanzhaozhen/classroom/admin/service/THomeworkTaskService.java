package com.shanzhaozhen.classroom.admin.service;

import com.shanzhaozhen.classroom.bean.THomeworkTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface THomeworkTaskService {

    Page<THomeworkTask> getTHomeworkTaskPage(Integer classroomId, String keyword, Pageable pageable);

    Map<String, Object> createTHomeworkTask(THomeworkTask tHomeworkTask);

    Map<String, Object> updateTHomeworkTask(THomeworkTask tHomeworkTask);

    Map<String, Object> deleteTHomeworkTask(Integer id);

    List<THomeworkTask> getHomeworkTaskListByClassroomId(Integer classroomId);

    THomeworkTask getTHomeworkTaskById(Integer id);

    Map<String, Object> getSubmitRateByHomeworkTaskId(Integer homeworkTaskId);

    int countTHomeworkTasksByClassroomId(Integer classroomId);
}

package com.shanzhaozhen.classroom.admin.service;

import com.shanzhaozhen.classroom.bean.TSignTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TSignTaskService {

    Page<TSignTask> getTSignTaskPage(Integer classroomId, String keyword, Pageable pageable);

    Map<String, Object> createTSignTask(TSignTask tSignTask);

    Map<String, Object> updateTSignTask(TSignTask tSignTask);

    Map<String, Object> deleteTSignTask(Integer id);

    List<TSignTask> getSignTaskListByClassroomId(Integer classroomId);

    TSignTask getTSignTaskById(Integer id);

    Map<String, Object> getAttendanceRateBySignTaskId(Integer signTaskId);

    int countTSignTasksByClassroomId(Integer classroomId);
}

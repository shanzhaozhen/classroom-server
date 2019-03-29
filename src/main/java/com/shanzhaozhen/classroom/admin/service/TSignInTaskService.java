package com.shanzhaozhen.classroom.admin.service;

import com.shanzhaozhen.classroom.bean.TSignInTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TSignInTaskService {

    Page<TSignInTask> getTSignInTaskPage(Integer classId, String keyword, Pageable pageable);

    Map<String, Object> createTSignInTask(TSignInTask tSignInTask);

    Map<String, Object> updateTSignInTask(TSignInTask tSignInTask);

    Map<String, Object> deleteTSignInTask(Integer id);

    List<TSignInTask> getSignInTaskListByClassId(Integer classId);

    TSignInTask getTSignInTaskById(Integer id);

    Map<String, Object> getAttendanceRateBySignInTaskId(Integer signInTaskId);
}

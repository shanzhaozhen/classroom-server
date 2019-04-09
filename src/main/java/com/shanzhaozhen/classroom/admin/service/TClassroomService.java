package com.shanzhaozhen.classroom.admin.service;

import com.shanzhaozhen.classroom.bean.TClassroom;
import com.shanzhaozhen.classroom.param.KeyValueParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TClassroomService {

    Page<TClassroom> getTClassroomPage(String keyword, Pageable pageable);

    Map<String, Object> createTClassroom(TClassroom tClassroom);

    Map<String, Object> updateTClassroom(TClassroom tClassroom);

    Map<String, Object> deleteTClassroom(Integer id);

    List<KeyValueParam> getTClassroomSimpleList();

    List<TClassroom> searchClassroom(String keyword);

    List<TClassroom> getMyClassroom();

    TClassroom getClassroomById(Integer id);

}

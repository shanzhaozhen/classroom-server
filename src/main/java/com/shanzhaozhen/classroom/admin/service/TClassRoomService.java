package com.shanzhaozhen.classroom.admin.service;

import com.shanzhaozhen.classroom.bean.TClassRoom;
import com.shanzhaozhen.classroom.param.KeyValueParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TClassRoomService {

    Page<TClassRoom> getTClassRoomPage(String keyword, Pageable pageable);

    Map<String, Object> createTClassRoom(TClassRoom tClassRoom);

    Map<String, Object> updateTClassRoom(TClassRoom tClassRoom);

    Map<String, Object> deleteTClassRoom(Integer id);

    List<KeyValueParam> getTClassRoomSimpleList();
}

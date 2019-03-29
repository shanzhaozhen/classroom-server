package com.shanzhaozhen.classroom.admin.controller;

import com.shanzhaozhen.classroom.admin.service.TClassroomService;
import com.shanzhaozhen.classroom.bean.TClassroom;
import com.shanzhaozhen.classroom.bean.THomeworkTask;
import com.shanzhaozhen.classroom.param.KeyValueParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class TClassroomController {

    @Autowired
    private TClassroomService tClassroomService;

    @GetMapping("/classroom")
    public Page<TClassroom> getTClassroomPage(String keyword, Pageable pageable) {
        Page<TClassroom> page = tClassroomService.getTClassroomPage(keyword, pageable);
        return page;
    }

    @PostMapping("/classroom")
    public Map<String, Object> createTClassroom(@RequestBody TClassroom tClassroom) {
        return tClassroomService.createTClassroom(tClassroom);
    }

    @PutMapping("/classroom")
    public Map<String, Object> updateClassRoom(@RequestBody TClassroom tClassroom) {
        return tClassroomService.updateTClassroom(tClassroom);
    }

    @DeleteMapping("/classroom/{id}")
    public Map<String, Object> deleteClassRoom(@PathVariable("id") Integer id) {
        return tClassroomService.deleteTClassroom(id);
    }

    @GetMapping("/classroom/simple")
    public List<KeyValueParam> getTClassroomList() {
        List<KeyValueParam> list = tClassroomService.getTClassroomSimpleList();
        return list;
    }

    @GetMapping("/classroom/search")
    public List<TClassroom> searchClassRoom(String keyword) {
        List<TClassroom> list = tClassroomService.searchClassRoom(keyword);
        return list;
    }

    @GetMapping("/classroom/my")
    public List<TClassroom> getTClassroom() {
        List<TClassroom> list = tClassroomService.getMyClassRoom();
        return list;
    }

    @GetMapping("/classroom/info/{id}")
    public TClassroom getTClassroomInfo(@PathVariable("id") Integer id) {
        TClassroom tClassroom = tClassroomService.getClassroomById(id);
        return tClassroom;
    }

}

package com.shanzhaozhen.classroom.admin.controller;

import com.shanzhaozhen.classroom.admin.service.TClassRoomService;
import com.shanzhaozhen.classroom.bean.TClassRoom;
import com.shanzhaozhen.classroom.param.KeyValueParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class TClassRoomController {

    @Autowired
    private TClassRoomService tClassRoomService;

    @GetMapping("/classroom")
    public Page<TClassRoom> getTClassRoomPage(String keyword, Pageable pageable) {
        Page<TClassRoom> page = tClassRoomService.getTClassRoomPage(keyword, pageable);
        return page;
    }

    @PostMapping("/classroom")
    public Map<String, Object> createTClassRoom(@RequestBody TClassRoom tClassRoom) {
        return tClassRoomService.createTClassRoom(tClassRoom);
    }

    @PutMapping("/classroom")
    public Map<String, Object> updateClassRoom(@RequestBody TClassRoom tClassRoom) {
        return tClassRoomService.updateTClassRoom(tClassRoom);
    }

    @DeleteMapping("/classroom/{id}")
    public Map<String, Object> deleteClassRoom(@PathVariable("id") Integer id) {
        return tClassRoomService.deleteTClassRoom(id);
    }

    @GetMapping("/classroom/simple")
    public List<KeyValueParam> getTClassRoomList() {
        List<KeyValueParam> list = tClassRoomService.getTClassRoomSimpleList();
        return list;
    }

}

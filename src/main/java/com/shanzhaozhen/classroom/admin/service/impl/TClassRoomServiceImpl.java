package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.service.SysUserService;
import com.shanzhaozhen.classroom.admin.service.TClassRoomService;
import com.shanzhaozhen.classroom.admin.service.TStudentService;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.TClassRoom;
import com.shanzhaozhen.classroom.admin.repository.TClassRoomRepository;
import com.shanzhaozhen.classroom.param.KeyValueParam;
import com.shanzhaozhen.classroom.utils.UserDetailsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TClassRoomServiceImpl implements TClassRoomService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TStudentService tStudentService;

    @Autowired
    private TClassRoomRepository tClassRoomRepository;

    @Override
    public Page<TClassRoom> getTClassRoomPage(String keyword, Pageable pageable) {
        keyword = "%" + keyword + "%";
        return tClassRoomRepository.findPageTClassRoomsByUserIdAndKeyword(1, keyword, keyword, pageable);
    }

    @Override
    @Transactional
    public Map<String, Object> createTClassRoom(TClassRoom tClassRoom) {
        Map<String, Object> map = new HashMap<>();
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            map.put("success", false);
            map.put("msg", "创建失败，没有找到该操作对应的用户");
            return map;
        }
        tClassRoom.setHeadmasterId(sysUser.getId());
        tClassRoomRepository.save(tClassRoom);
        map.put("success", true);
        map.put("msg", "创建成功");
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> updateTClassRoom(TClassRoom tClassRoom) {
        Map<String, Object> map = new HashMap<>();
        TClassRoom temp = tClassRoomRepository.findTClassRoomById(tClassRoom.getId());
        if (temp == null) {
            map.put("success", false);
            map.put("msg", "该班级不存在");
            return map;
        }
        BeanUtils.copyProperties(temp, tClassRoom, "name", "outline", "announce");
        tClassRoomRepository.save(tClassRoom);
        map.put("success", true);
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> deleteTClassRoom(Integer id) {
        Map<String, Object> map = new HashMap<>();
        TClassRoom temp = tClassRoomRepository.findTClassRoomById(id);
        if (temp == null) {
            map.put("success", false);
            map.put("msg", "该班级不存在");
            return map;
        }
        tClassRoomRepository.deleteById(id);
        tStudentService.removeAllStudentByClassId(id);
        map.put("success", true);
        map.put("msg", "删除成功");
        return map;
    }

    @Override
    public List<KeyValueParam> getTClassRoomSimpleList() {
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            return null;
        }
        List<KeyValueParam> list = tClassRoomRepository.findSimpleTClassRoomsByHeadmasterId(sysUser.getId());
        return list;
    }

}

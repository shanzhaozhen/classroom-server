package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.service.SysUserService;
import com.shanzhaozhen.classroom.admin.service.TClassroomService;
import com.shanzhaozhen.classroom.admin.service.TStudentService;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.TClassroom;
import com.shanzhaozhen.classroom.admin.repository.TClassroomRepository;
import com.shanzhaozhen.classroom.bean.TStudent;
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
public class TClassroomServiceImpl implements TClassroomService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TStudentService tStudentService;

    @Autowired
    private TClassroomRepository tClassroomRepository;

    @Override
    public Page<TClassroom> getTClassroomPage(String keyword, Pageable pageable) {
        keyword = "%" + keyword + "%";
        return tClassroomRepository.findPageTClassroomsByUserIdAndKeyword(1, keyword, keyword, pageable);
    }

    @Override
    @Transactional
    public Map<String, Object> createTClassroom(TClassroom tClassroom) {
        Map<String, Object> map = new HashMap<>();
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            map.put("success", false);
            map.put("msg", "创建失败，没有找到该操作对应的用户");
            return map;
        }
        tClassroom.setHeadmasterId(sysUser.getId());
        tClassroomRepository.save(tClassroom);
        map.put("success", true);
        map.put("msg", "创建成功");
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> updateTClassroom(TClassroom tClassroom) {
        Map<String, Object> map = new HashMap<>();
        TClassroom temp = tClassroomRepository.findTClassroomById(tClassroom.getId());
        if (temp == null) {
            map.put("success", false);
            map.put("msg", "该班级不存在");
            return map;
        }
        BeanUtils.copyProperties(temp, tClassroom, "name", "outline", "announce");
        tClassroomRepository.save(tClassroom);
        map.put("success", true);
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> deleteTClassroom(Integer id) {
        Map<String, Object> map = new HashMap<>();
        TClassroom temp = tClassroomRepository.findTClassroomById(id);
        if (temp == null) {
            map.put("success", false);
            map.put("msg", "该班级不存在");
            return map;
        }
        tClassroomRepository.deleteById(id);
        tStudentService.removeAllStudentByClassId(id);
        map.put("success", true);
        map.put("msg", "删除成功");
        return map;
    }

    @Override
    public List<KeyValueParam> getTClassroomSimpleList() {
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            return null;
        }
        List<KeyValueParam> list = tClassroomRepository.findSimpleTClassroomsByHeadmasterId(sysUser.getId());
        return list;
    }

    @Override
    public List<TClassroom> searchClassRoom(String keyword) {
        keyword = "%" + keyword + "%";
        return tClassroomRepository.findTClassroomsByKeyword(keyword, keyword);
    }

    @Override
    public List<TClassroom> getMyClassRoom() {
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            return null;
        }
        List<TClassroom> list = tClassroomRepository.findTClassroomsByHeadmasterIdAndStudentId(sysUser.getId(), sysUser.getId());
        return list;
    }

    @Override
    public TClassroom getClassroomById(Integer id) {
        return tClassroomRepository.findTClassroomInfoById(id);
    }

}

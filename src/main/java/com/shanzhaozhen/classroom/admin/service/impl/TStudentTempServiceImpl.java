package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.repository.TStudentRepository;
import com.shanzhaozhen.classroom.admin.repository.TStudentTempRepository;
import com.shanzhaozhen.classroom.admin.service.*;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.TClassroom;
import com.shanzhaozhen.classroom.bean.TStudent;
import com.shanzhaozhen.classroom.bean.TStudentTemp;
import com.shanzhaozhen.classroom.utils.UserDetailsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class TStudentTempServiceImpl implements TStudentTempService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TClassroomService tClassroomService;

    @Autowired
    private TStudentService tStudentService;

    @Autowired
    private TStudentTempRepository tStudentTempRepository;


    @Override
    public Page<TStudentTemp> getTStudentTempPage(Pageable pageable) {

        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            return null;
        }

        Page<TStudentTemp> page = tStudentTempRepository.findPageTStudentTemps(sysUser.getId(), pageable);
        return page;
    }

    @Override
    public TStudentTemp findTStudentTempById(Integer id) {
        return tStudentTempRepository.findTStudentTempById(id);
    }

    @Override
    public TStudentTemp findTStudentTempByClassroomIdAndStudentId(Integer classroomId, Integer studentId) {
        return tStudentTempRepository.findTStudentTempByClassroomIdAndStudentId(classroomId, studentId);
    }

    @Override
    @Transactional
    public Map<String, Object> addTStudentTemp(TStudentTemp tStudentTemp) {
        Map<String, Object> map = new HashMap<>();
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            map.put("success", false);
            map.put("msg", "加入失败，没有找到该操作对应的用户");
            return map;
        }

        if (tStudentTemp.getClassroomId() == null) {
            map.put("success", false);
            map.put("msg", "无效班级id");
            return map;
        }

        TClassroom tClassroom = tClassroomService.getClassroomById(tStudentTemp.getClassroomId());
        if (tClassroom == null) {
            map.put("success", false);
            map.put("msg", "班级不存在");
            return map;
        }

        TStudentTemp tempStudentTemp = tStudentTempRepository.findTStudentTempByClassroomIdAndStudentId(tStudentTemp.getClassroomId(), sysUser.getId());
        if (tempStudentTemp != null) {
            map.put("success", false);
            map.put("msg", "请勿重复申请");
            return map;
        }

        TStudent tStudent = tStudentService.findTStudentByClassroomIdAndStudentId(tStudentTemp.getClassroomId(), sysUser.getId());
        if (tStudent != null) {
            map.put("success", false);
            map.put("msg", "请勿重复加入");
            return map;
        }

        tStudentTemp.setStudentId(sysUser.getId());
        tStudentTempRepository.save(tStudentTemp);
        map.put("success", true);
        map.put("msg", "申请成功");
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> passStudent(Integer id) {
        Map<String, Object> map = new HashMap<>();

        if (id == null) {
            map.put("success", false);
            map.put("msg", "无效申请id");
            return map;
        }

        TStudentTemp tStudentTemp = tStudentTempRepository.findTStudentTempById(id);
        if (tStudentTemp == null) {
            map.put("success", false);
            map.put("msg", "操作对象不存在");
            return map;
        }

        TStudent tStudent = tStudentService.findTStudentByClassroomIdAndStudentId(tStudentTemp.getClassroomId(), tStudentTemp.getStudentId());
        if (tStudent != null) {
            map.put("success", false);
            map.put("msg", "已同意");
            return map;
        }

        tStudentService.savePassStudent(tStudentTemp);

        tStudentTempRepository.delete(tStudentTemp);

        map.put("success", true);
        map.put("msg", "通过成功");
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> deleteTStudentTemp(Integer id) {
        Map<String, Object> map = new HashMap<>();

        tStudentTempRepository.deleteTStudentTempById(id);
        map.put("success", true);
        map.put("msg", "已拒绝");
        return map;
    }


}

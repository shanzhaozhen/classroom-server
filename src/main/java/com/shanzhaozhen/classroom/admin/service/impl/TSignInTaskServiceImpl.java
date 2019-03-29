package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.repository.TSignInTaskRepository;
import com.shanzhaozhen.classroom.admin.service.SysUserService;
import com.shanzhaozhen.classroom.admin.service.TSignInTaskService;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.TSignInTask;
import com.shanzhaozhen.classroom.utils.UserDetailsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TSignInTaskServiceImpl implements TSignInTaskService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TSignInTaskRepository tSignInTaskRepository;

    @Override
    public Page<TSignInTask> getTSignInTaskPage(Integer classId, String keyword, Pageable pageable) {
        keyword = "%" + keyword + "%";
        Page<TSignInTask> page = null;
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            return null;
        }
        if (classId == null) {
            page = tSignInTaskRepository.findTSignInTasksByCreaterIdAndKeyword(sysUser.getId(), keyword, keyword, pageable);
        } else {
            page = tSignInTaskRepository.findTSignInTasksByCreaterIdAndClassIdAndKeyword(sysUser.getId(), classId, keyword, keyword, pageable);
        }
        return page;
    }

    @Override
    @Transactional
    public Map<String, Object> createTSignInTask(TSignInTask tSignInTask) {
        Map<String, Object> map = new HashMap<>();
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            map.put("success", false);
            map.put("msg", "创建失败，没有找到该操作对应的用户");
            return map;
        }
        tSignInTask.setCreaterId(sysUser.getId());
        tSignInTaskRepository.save(tSignInTask);
        map.put("success", true);
        map.put("msg", "签到任务创建成功");
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> updateTSignInTask(TSignInTask tSignInTask) {
        Map<String, Object> map = new HashMap<>();
        TSignInTask temp = tSignInTaskRepository.findTSignInTaskById(tSignInTask.getId());
        if (temp == null) {
            map.put("success", false);
            map.put("msg", "该签到任务不存在");
            return map;
        }
        BeanUtils.copyProperties(temp, tSignInTask, "name", "outline", "classId",
                "startDate", "endDate", "signInType", "address", "longitude", "latitude", "scope", "announce");
        tSignInTaskRepository.save(tSignInTask);
        map.put("success", true);
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> deleteTSignInTask(Integer id) {
        Map<String, Object> map = new HashMap<>();
        TSignInTask temp = tSignInTaskRepository.findTSignInTaskById(id);
        if (temp == null) {
            map.put("success", false);
            map.put("msg", "该签到任务不存在");
            return map;
        }
        tSignInTaskRepository.deleteById(id);
        map.put("success", true);
        map.put("msg", "删除成功");
        return map;
    }

    @Override
    public List<TSignInTask> getSignInTaskListByClassId(Integer classId) {
        return tSignInTaskRepository.findTSignInTasksByClassIdAndAnnounceIsTrue(classId);

    }

    @Override
    public TSignInTask getTSignInTaskById(Integer id) {
        return tSignInTaskRepository.findTSignInTaskById(id);
    }

    @Override
    public Map<String, Object> getAttendanceRateBySignInTaskId(Integer signInTaskId) {
        Map<String, Object> map = new HashMap<>();

        if (signInTaskId == null) {
            map.put("success", false);
            map.put("msg", "获取失败");
            return map;
        }

        int studentNumber = tSignInTaskRepository.countStudentNumberBySignInTaskId(signInTaskId);

        if (studentNumber == 0) {
            map.put("success", false);
            map.put("msg", "无学生班级");
            return map;
        }

        int attendanceNumber = tSignInTaskRepository.countAttendanceNumberBySignInTaskId(signInTaskId);

        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
        //可以设置精确几位小数
        decimalFormat.setMaximumFractionDigits(2);
        //模式 例如四舍五入
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num = attendanceNumber / studentNumber * 100;
        String attendanceRate = decimalFormat.format(accuracy_num) + "%";

        map.put("success", true);
        map.put("msg", "获取成功");
        map.put("attendanceRate", attendanceRate);
        map.put("attendanceNumber", attendanceNumber);
        map.put("studentNumber", studentNumber);
        return map;
    }
}

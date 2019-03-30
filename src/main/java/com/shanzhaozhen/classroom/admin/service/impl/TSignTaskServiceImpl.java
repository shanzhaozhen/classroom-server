package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.repository.TSignTaskRepository;
import com.shanzhaozhen.classroom.admin.service.SysUserService;
import com.shanzhaozhen.classroom.admin.service.TSignTaskService;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.TSignTask;
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
public class TSignTaskServiceImpl implements TSignTaskService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TSignTaskRepository tSignTaskRepository;

    @Override
    public Page<TSignTask> getTSignTaskPage(Integer classId, String keyword, Pageable pageable) {
        keyword = "%" + keyword + "%";
        Page<TSignTask> page = null;
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            return null;
        }
        if (classId == null) {
            page = tSignTaskRepository.findTSignTasksByCreaterIdAndKeyword(sysUser.getId(), keyword, keyword, pageable);
        } else {
            page = tSignTaskRepository.findTSignTasksByCreaterIdAndClassIdAndKeyword(sysUser.getId(), classId, keyword, keyword, pageable);
        }
        return page;
    }

    @Override
    @Transactional
    public Map<String, Object> createTSignTask(TSignTask tSignTask) {
        Map<String, Object> map = new HashMap<>();
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            map.put("success", false);
            map.put("msg", "创建失败，没有找到该操作对应的用户");
            return map;
        }
        tSignTask.setCreaterId(sysUser.getId());
        tSignTaskRepository.save(tSignTask);
        map.put("success", true);
        map.put("msg", "签到任务创建成功");
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> updateTSignTask(TSignTask tSignTask) {
        Map<String, Object> map = new HashMap<>();
        TSignTask temp = tSignTaskRepository.findTSignTaskById(tSignTask.getId());
        if (temp == null) {
            map.put("success", false);
            map.put("msg", "该签到任务不存在");
            return map;
        }
        BeanUtils.copyProperties(temp, tSignTask, "name", "outline", "classId",
                "startDate", "endDate", "signType", "address", "longitude", "latitude", "scope", "announce");
        tSignTaskRepository.save(tSignTask);
        map.put("success", true);
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> deleteTSignTask(Integer id) {
        Map<String, Object> map = new HashMap<>();
        TSignTask temp = tSignTaskRepository.findTSignTaskById(id);
        if (temp == null) {
            map.put("success", false);
            map.put("msg", "该签到任务不存在");
            return map;
        }
        tSignTaskRepository.deleteById(id);
        map.put("success", true);
        map.put("msg", "删除成功");
        return map;
    }

    @Override
    public List<TSignTask> getSignTaskListByClassroomId(Integer classId) {
        return tSignTaskRepository.findTSignTasksByClassIdAndAnnounceIsTrue(classId);

    }

    @Override
    public TSignTask getTSignTaskById(Integer id) {
        return tSignTaskRepository.findTSignTaskById(id);
    }

    @Override
    public Map<String, Object> getAttendanceRateBySignTaskId(Integer signTaskId) {
        Map<String, Object> map = new HashMap<>();

        if (signTaskId == null) {
            map.put("success", false);
            map.put("msg", "获取失败");
            return map;
        }

        int studentNumber = tSignTaskRepository.countStudentNumberBySignTaskId(signTaskId);

        if (studentNumber == 0) {
            map.put("success", false);
            map.put("msg", "无学生班级");
            return map;
        }

        int attendanceNumber = tSignTaskRepository.countAttendanceNumberBySignTaskId(signTaskId);

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

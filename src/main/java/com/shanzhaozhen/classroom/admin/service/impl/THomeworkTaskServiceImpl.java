package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.repository.THomeworkTaskRepository;
import com.shanzhaozhen.classroom.admin.service.SysUserService;
import com.shanzhaozhen.classroom.admin.service.THomeworkTaskService;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.THomeworkTask;
import com.shanzhaozhen.classroom.utils.StudentUtils;
import com.shanzhaozhen.classroom.utils.UserDetailsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class THomeworkTaskServiceImpl implements THomeworkTaskService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private THomeworkTaskRepository tHomeworkTaskRepository;


    @Override
    public Page<THomeworkTask> getTHomeworkTaskPage(Integer classroomId, String keyword, Pageable pageable) {
        keyword = "%" + keyword + "%";
        Page<THomeworkTask> page;
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            return null;
        }
        if (classroomId == null) {
            page = tHomeworkTaskRepository.findTHomeworkTasksByCreaterIdAndKeyword(sysUser.getId(), keyword, keyword, pageable);
        } else {
            page = tHomeworkTaskRepository.findTHomeworkTasksByCreaterIdAndClassroomIdAndKeyword(sysUser.getId(), classroomId, keyword, keyword, pageable);
        }
        return page;
    }

    @Override
    public Map<String, Object> createTHomeworkTask(THomeworkTask tHomeworkTask) {
        Map<String, Object> map = new HashMap<>();
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            map.put("success", false);
            map.put("msg", "创建失败，没有找到该操作对应的用户");
            return map;
        }
        tHomeworkTask.setCreaterId(sysUser.getId());
        tHomeworkTaskRepository.save(tHomeworkTask);
        map.put("success", true);
        map.put("msg", "作业任务创建成功");
        return map;
    }

    @Override
    public Map<String, Object> updateTHomeworkTask(THomeworkTask tHomeworkTask) {
        Map<String, Object> map = new HashMap<>();
        THomeworkTask temp = tHomeworkTaskRepository.findTHomeworkTaskById(tHomeworkTask.getId());
        if (temp == null) {
            map.put("success", false);
            map.put("msg", "该作业任务不存在");
            return map;
        }
        BeanUtils.copyProperties(temp, tHomeworkTask, "name", "outline", "classroomId",
                "startDate", "endDate", "announce");
        tHomeworkTaskRepository.save(tHomeworkTask);
        map.put("success", true);
        return map;
    }

    @Override
    public Map<String, Object> deleteTHomeworkTask(Integer id) {
        Map<String, Object> map = new HashMap<>();
        THomeworkTask temp = tHomeworkTaskRepository.findTHomeworkTaskById(id);
        if (temp == null) {
            map.put("success", false);
            map.put("msg", "该作业任务不存在");
            return map;
        }
        tHomeworkTaskRepository.deleteById(id);
        map.put("success", true);
        map.put("msg", "删除成功");
        return map;
    }

    @Override
    public List<THomeworkTask> getHomeworkTaskListByClassroomId(Integer classroomId) {
        return tHomeworkTaskRepository.findTHomeworkTasksByClassroomIdAndAnnounceIsTrue(classroomId);
    }

    @Override
    public THomeworkTask getTHomeworkTaskById(Integer id) {
        return tHomeworkTaskRepository.findTHomeworkTaskById(id);
    }

    @Override
    public Map<String, Object> getSubmitRateByHomeworkTaskId(Integer homeworkTaskId) {
        Map<String, Object> map = new HashMap<>();

        if (homeworkTaskId == null) {
            map.put("success", false);
            map.put("msg", "获取失败");
            return map;
        }

        int studentNumber = tHomeworkTaskRepository.countStudentNumberByHomeworkTaskId(homeworkTaskId);

        if (studentNumber == 0) {
            map.put("success", false);
            map.put("msg", "无学生班级");
            return map;
        }

        int submitNumber = tHomeworkTaskRepository.countSubmitNumberByHomeworkTaskId(homeworkTaskId);

        String submitRate = StudentUtils.calPercent(submitNumber, studentNumber);

        map.put("success", true);
        map.put("msg", "获取成功");
        map.put("commitRate", submitRate);
        map.put("commitNumber", submitNumber);
        map.put("studentNumber", studentNumber);
        return map;
    }

    @Override
    public int countTHomeworkTasksByClassroomId(Integer classroomId) {
        return tHomeworkTaskRepository.countTHomeworkTasksByClassroomId(classroomId);
    }

}

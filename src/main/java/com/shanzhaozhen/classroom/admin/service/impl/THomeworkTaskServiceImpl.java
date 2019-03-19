package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.repository.THomeworkTaskRepository;
import com.shanzhaozhen.classroom.admin.service.SysUserService;
import com.shanzhaozhen.classroom.admin.service.THomeworkTaskService;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.THomeworkTask;
import com.shanzhaozhen.classroom.utils.UserDetailsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class THomeworkTaskServiceImpl implements THomeworkTaskService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private THomeworkTaskRepository tHomeworkTaskRepository;


    @Override
    public Page<THomeworkTask> getTHomeworkTaskPage(Integer classId, String keyword, Pageable pageable) {
        keyword = "%" + keyword + "%";
        Page<THomeworkTask> page = null;
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            return null;
        }
        if (classId == null) {
            page = tHomeworkTaskRepository.findTHomeworkTasksByCreaterIdAndKeyword(sysUser.getId(), keyword, keyword, pageable);
        } else {
            page = tHomeworkTaskRepository.findTHomeworkTasksByCreaterIdAndClassIdAndKeyword(sysUser.getId(), classId, keyword, keyword, pageable);
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
        BeanUtils.copyProperties(temp, tHomeworkTask, "name", "outline", "classId",
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

}
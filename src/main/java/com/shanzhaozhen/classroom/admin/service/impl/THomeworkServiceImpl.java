package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.repository.THomeworkRepository;
import com.shanzhaozhen.classroom.admin.service.SysUserService;
import com.shanzhaozhen.classroom.admin.service.THomeworkService;
import com.shanzhaozhen.classroom.admin.service.THomeworkTaskService;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.THomework;
import com.shanzhaozhen.classroom.bean.THomeworkTask;
import com.shanzhaozhen.classroom.utils.UserDetailsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class THomeworkServiceImpl implements THomeworkService {

    @Autowired
    private THomeworkRepository tHomeworkRepository;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private THomeworkTaskService tHomeworkTaskService;


    @Override
    public Page<THomework> getTHomeworkPage(Integer homeworkTaskId, String keyword, Pageable pageable) {
        keyword = "%" + keyword + "%";
        Page<THomework> page = tHomeworkRepository.findTHomeworksByHomeworkTaskIdAndKeyword(homeworkTaskId, keyword, keyword, pageable);
        return page;
    }

    @Override
    public Map<String, Object> giveHomeworkScore(Integer homeworkId, Integer score) {
        Map<String, Object> map = new HashMap<>();

        THomework tHomework = tHomeworkRepository.findTHomeworkById(homeworkId);

        if (tHomework == null) {
            map.put("success", false);
            map.put("msg", "该份作业不存在");
        }

        tHomework.setScore(score);
        tHomeworkRepository.save(tHomework);
        map.put("success", true);
        map.put("msg", "评分成功");
        return map;
    }

    @Override
    public Map<String, Object> saveTHomework(THomework tHomework) {
        Map<String, Object> map = new HashMap<>();

        if (tHomework.getHomeworkTaskId() == null) {
            map.put("success", false);
            map.put("msg", "没有找到相关的作业任务");
            return map;
        }

        THomeworkTask tHomeworkTask = tHomeworkTaskService.getTHomeworkTaskById(tHomework.getHomeworkTaskId());
        if (tHomeworkTask == null) {
            if (tHomework.getHomeworkTaskId() == null) {
                map.put("success", false);
                map.put("msg", "没有找到相关的作业任务");
                return map;
            }
        }

        Date now = new Date();

        if (now.before(tHomeworkTask.getStartDate())) {
            map.put("success", false);
            map.put("msg", "作业任务还没开始");
            return map;
        }

        if (now.after(tHomeworkTask.getEndDate())) {
            map.put("success", false);
            map.put("msg", "任务任务已经结束");
            return map;
        }

        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            map.put("success", false);
            map.put("msg", "提交失败失败");
            return map;
        }

        THomework temp = tHomeworkRepository.findTHomeworkByCreaterIdAndHomeworkTaskId(sysUser.getId(), tHomework.getHomeworkTaskId());

        if (temp != null) {
            map.put("success", false);
            map.put("msg", "请勿重复提交");
        }

        tHomework.setCreaterId(sysUser.getId());
        tHomeworkRepository.save(tHomework);

        map.put("success", true);
        map.put("msg", "提交成功");
        return map;
    }

    @Override
    public Map<String, Object> getTHomework(Integer id) {
        Map<String, Object> map = new HashMap<>();

        if (id == null) {
            map.put("success", false);
            map.put("msg", "获取作业数据失败");
            return map;
        }

        THomework tHomework = tHomeworkRepository.findTHomeworkAndInfoAndFileInfoById(id);

        if (tHomework == null) {
            map.put("success", false);
            map.put("msg", "获取作业数据失败");
            return map;
        }

        map.put("success", true);
        map.put("msg", "获取作业数据成功");
        map.put("data", tHomework);
        return map;
    }

    public Map<String, Object> getTHomeworkBySignInTaskId(Integer homeworkTaskId) {

        Map<String, Object> map = new HashMap<>();

        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            map.put("success", false);
            map.put("msg", "获取失败");
            return map;
        }

        THomework tHomework = tHomeworkRepository.findTHomeworkByCreaterIdAndHomeworkTaskId(sysUser.getId(), homeworkTaskId);

        if (tHomework == null) {
            map.put("success", false);
            map.put("msg", "未交作业");
            return map;
        }

        map.put("success", true);
        map.put("msg", "已交作业");
        map.put("data", tHomework);
        return map;
    }

}

package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.repository.TSignInRepository;
import com.shanzhaozhen.classroom.admin.service.SysUserService;
import com.shanzhaozhen.classroom.admin.service.TSignInService;
import com.shanzhaozhen.classroom.admin.service.TSignInTaskService;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.TSignIn;
import com.shanzhaozhen.classroom.bean.TSignInTask;
import com.shanzhaozhen.classroom.utils.LocationUtils;
import com.shanzhaozhen.classroom.utils.UserDetailsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TSignInServiceImpl implements TSignInService {

    @Autowired
    private TSignInRepository tSignInRepository;

    @Autowired
    private TSignInTaskService tSignInTaskService;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Page<TSignIn> getTSignInPage(Integer signInTaskId, String keyword, Pageable pageable) {
        keyword = "%" + keyword + "%";
        Page<TSignIn> page = tSignInRepository.findTSignInsBySignInTaskIdAndKeyword(signInTaskId, keyword, keyword, pageable);
        return page;
    }

    @Override
    public Map<String, Object> signIn(TSignIn tSignIn) {
        Map<String, Object> map = new HashMap<>();
        if (tSignIn.getSignInTaskId() == null) {
            map.put("success", false);
            map.put("msg", "没有找到相关的考勤任务");
            return map;
        }

        TSignInTask tSignInTask = tSignInTaskService.getTSignInTaskById(tSignIn.getSignInTaskId());
        if (tSignInTask == null) {
            map.put("success", false);
            map.put("msg", "没有找到相关的考勤任务");
            return map;
        }

        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            map.put("success", false);
            map.put("msg", "签到失败");
            return map;
        }

        TSignIn temp = tSignInRepository.findTSignInByCreaterIdAndSignInTaskId(sysUser.getId(), tSignIn.getSignInTaskId());
        if (temp != null) {
            map.put("success", false);
            map.put("msg", "请勿重复签到");
            return map;
        }

        Date now = new Date();

        if (now.before(tSignInTask.getStartDate())) {
            map.put("success", false);
            map.put("msg", "考勤还没开始");
            return map;
        }

        if (now.after(tSignInTask.getEndDate())) {
            map.put("success", false);
            map.put("msg", "考勤已经结束");
            return map;
        }
        double m = LocationUtils.distanceByLongNLat(tSignInTask.getLongitude(), tSignInTask.getLatitude(),
                tSignIn.getLongitude(), tSignIn.getLatitude());
        if (LocationUtils.distanceByLongNLat(tSignInTask.getLongitude(), tSignInTask.getLatitude(),
                tSignIn.getLongitude(), tSignIn.getLatitude()) > tSignInTask.getScope()) {
            map.put("success", false);
            map.put("msg", "不在签到的范围内");
            return map;
        }

        tSignInRepository.save(tSignIn);
        map.put("success", true);
        map.put("msg", "签到成功");
        return map;
    }

    @Override
    public Map<String, Object> getTSignInBySignInTaskId(Integer signInTaskId) {

        Map<String, Object> map = new HashMap<>();

        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            map.put("success", false);
            map.put("msg", "获取失败");
            return map;
        }

        TSignIn tSignIn = tSignInRepository.findTSignInByCreaterIdAndSignInTaskId(sysUser.getId(), signInTaskId);

        if (tSignIn == null) {
            map.put("success", false);
            map.put("msg", "未签到");
            return map;
        }

        map.put("success", true);
        map.put("msg", "已签到");
        map.put("data", tSignIn);
        return map;
    }

}

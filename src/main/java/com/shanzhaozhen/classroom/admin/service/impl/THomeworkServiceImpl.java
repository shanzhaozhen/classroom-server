package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.repository.THomeworkRepository;
import com.shanzhaozhen.classroom.admin.service.SysUserService;
import com.shanzhaozhen.classroom.admin.service.THomeworkService;
import com.shanzhaozhen.classroom.admin.service.THomeworkTaskService;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.THomework;
import com.shanzhaozhen.classroom.bean.THomeworkTask;
import com.shanzhaozhen.classroom.param.ExcelParam;
import com.shanzhaozhen.classroom.utils.PoiUtils;
import com.shanzhaozhen.classroom.utils.UserDetailsUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public Map<String, Object> getTHomeworkByHomeworkTaskId(Integer homeworkTaskId) {

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

    @Override
    public void exporHomeworkDataByHomeworkTaskId(Integer homeworkTaskId, HttpServletResponse httpServletResponse) {
        THomeworkTask tHomeworkTask = tHomeworkTaskService.getTHomeworkTaskById(homeworkTaskId);

        if (tHomeworkTask == null) {
            return;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<THomework> list = tHomeworkRepository.findTHomeworksByHomeworkTaskId(homeworkTaskId);
        String[] rowName = new String[] {"序号", "学号", "姓名", "提交时间", "评分"};
        List<String> rowNameList = Arrays.asList(rowName);
        String footer = null;

        List<List<Object>> dataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<Object> item = new ArrayList<>();
            THomework tHomework = list.get(i);
            item.add("" + (i + 1));
            item.add(tHomework.getNumber());
            item.add(tHomework.getFullName());
            if (tHomework.getCreatedDate() == null) {
                item.add("(未提交)");
            } else {
                item.add(simpleDateFormat.format(tHomework.getCreatedDate()));
            }
            dataList.add(item);
        }
        if (list.size() > 0) {
            Map<String, Object> result = tHomeworkTaskService.getSubmitRateByHomeworkTaskId(homeworkTaskId);
            if ((boolean) result.get("success") == true) {
                footer = "班级人数：" + result.get("studentNumber") + "，提交人数：" + result.get("commitNumber") +  "，提交率：" + result.get("commitRate");
            }
        }
        Workbook workbook = PoiUtils.writeExcel(new ExcelParam(null, tHomeworkTask.getName() + "-出勤数据", rowNameList, dataList, footer));
        PoiUtils.exportExcel(httpServletResponse, workbook);
    }

}

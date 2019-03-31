package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.repository.TStudentRepository;
import com.shanzhaozhen.classroom.admin.service.*;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.TClassroom;
import com.shanzhaozhen.classroom.bean.TStudent;
import com.shanzhaozhen.classroom.bean.TStudentTemp;
import com.shanzhaozhen.classroom.param.ExcelParam;
import com.shanzhaozhen.classroom.utils.PoiUtils;
import com.shanzhaozhen.classroom.utils.StudentUtils;
import com.shanzhaozhen.classroom.utils.UserDetailsUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TStudentServiceImpl implements TStudentService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private THomeworkTaskService tHomeworkTaskService;

    @Autowired
    private THomeworkService tHomeworkService;

    @Autowired
    private TSignTaskService tSignTaskService;

    @Autowired
    private TSignService tSignService;

    @Autowired
    private TClassroomService tClassroomService;

    @Autowired
    private TStudentRepository tStudentRepository;

    @Override
    public Page<TStudent> getTStudentPage(Integer classroomId, String keyword, Pageable pageable) {
        keyword = "%" + keyword + "%";
        Page<TStudent> page = tStudentRepository.findPageTStudentsByClassroomIdAndKeyword(classroomId, keyword, keyword, pageable);

        List<TStudent> list = page.getContent();

        this.getStudentOtherInfo(classroomId, list);

        return page;
    }

    @Override
    public void getStudentOtherInfo(Integer classroomId, List<TStudent> list) {
        //总作业任务数
        int totalCommitNumber = tHomeworkTaskService.countTHomeworkTasksByClassroomId(classroomId);

        //总考勤任务数
        int totalAttendanceNumber = tSignTaskService.countTSignTasksByClassroomId(classroomId);

        for (TStudent tStudent : list) {
            if (totalCommitNumber > 0) {
                //计算作业平均分
//                int sumScore = tHomeworkService.getSumScoreByStudentIdAndClassroomId(tStudent.getStudentId(), classroomId);
//                float averageScore = sumScore / totalCommitNumber;
                float averageScore = tHomeworkService.getAvgScoreByStudentIdAndClassroomId(tStudent.getStudentId(), classroomId);

                //计算提交率
                int commitNumber = tHomeworkService.countTHomeworksByStudentIdAndClassroomId(tStudent.getStudentId(), classroomId);
                String commitRate = StudentUtils.calPercent(commitNumber, totalCommitNumber);

                tStudent.setAverageScore(averageScore);
                tStudent.setTotalCommitNumber(totalCommitNumber);
                tStudent.setCommitNumber(commitNumber);
                tStudent.setCommitRate(commitRate);
            }


            if (totalAttendanceNumber > 0) {
                //计算出勤率
                int attendanceNumber = tSignService.countTSignsByStudentIdAndClassroomId(tStudent.getStudentId(), classroomId);
                String attendanceRate = StudentUtils.calPercent(attendanceNumber, totalAttendanceNumber);

                tStudent.setTotalAttendanceNumber(totalAttendanceNumber);
                tStudent.setAttendanceNumber(attendanceNumber);
                tStudent.setAttendanceRate(attendanceRate);
            }
        }
    }

    @Override
    @Transactional
    public Map<String, Object> addTStudent(TStudent tStudent) {
        Map<String, Object> map = new HashMap<>();
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            map.put("success", false);
            map.put("msg", "加入失败，没有找到该操作对应的用户");
            return map;
        }

        if (tStudent.getClassroomId() == null) {
            map.put("success", false);
            map.put("msg", "无效班级id");
            return map;
        }

        TStudent tempStudent = tStudentRepository.findTStudentByClassroomIdAndStudentId(tStudent.getClassroomId(), sysUser.getId());
        if (tempStudent != null) {
            map.put("success", false);
            map.put("msg", "请勿重复加入");
            return map;
        }

        tStudent.setStudentId(sysUser.getId());
        tStudentRepository.save(tStudent);
        map.put("success", true);
        map.put("msg", "加入成功");
        return map;

    }

    @Override
    @Transactional
    public Map<String, Object> delTStudent(Integer id) {
        Map<String, Object> map = new HashMap<>();

        TStudent tStudent = tStudentRepository.findTStudentById(id);

        if (tStudent == null) {
            map.put("success", false);
            map.put("msg", "该同学不在该班级或不存在");
            return map;
        }

        tStudentRepository.deleteById(id);

        map.put("success", true);
        map.put("msg", "移出班级成功");
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> leaveClass(Integer classroomId, Integer studentId) {
        Map<String, Object> map = new HashMap<>();

        TStudent tStudent = tStudentRepository.findTStudentByClassroomIdAndStudentId(classroomId, studentId);

        if (tStudent == null) {
            map.put("success", false);
            map.put("msg", "该同学不在该班级或不存在");
            return map;
        }

        tStudentRepository.delete(tStudent);

        map.put("success", true);
        map.put("msg", "退出班级成功");
        return map;
    }

    @Override
    @Transactional
    public void removeAllStudentByClassroomId(Integer classroomId) {
        tStudentRepository.deleteByClassroomId(classroomId);
    }

    @Override
    public void exportStudentDataByClassroomId(Integer classroomId, HttpServletResponse httpServletResponse) {
        TClassroom tClassroom = tClassroomService.getClassroomById(classroomId);

        if (tClassroom == null) {
            return;
        }

        List<TStudent> list = tStudentRepository.findListTStudentsByClassroomId(classroomId);

        this.getStudentOtherInfo(classroomId, list);

        String[] rowName = new String[] {"序号", "学号", "姓名", "作业提交次数", "作业提交率", "作业平均分", "出勤数", "出勤率"};
        List<String> rowNameList = Arrays.asList(rowName);
        String footer = null;

        List<List<Object>> dataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<Object> item = new ArrayList<>();
            TStudent tHomework = list.get(i);
            item.add("" + (i + 1));
            item.add(tHomework.getNumber());
            item.add(tHomework.getFullName());
            item.add(tHomework.getCommitNumber());
            item.add(tHomework.getCommitRate());
            item.add(tHomework.getAverageScore());
            item.add(tHomework.getAttendanceNumber());
            item.add(tHomework.getAttendanceRate());
            dataList.add(item);
        }
        if (list.size() > 0) {
            TStudent temp = list.get(0);
            footer = "班级人数：" + list.size() + "，作业任务数：" + temp.getTotalCommitNumber() + "，考勤任务数：" + temp.getTotalAttendanceNumber();
        }
        Workbook workbook = PoiUtils.writeExcel(new ExcelParam(null, tClassroom.getName() + "-汇总数据", rowNameList, dataList, footer));
        PoiUtils.exportExcel(httpServletResponse, workbook);
    }

    @Override
    public TStudent findTStudentByClassroomIdAndStudentId(Integer classroomId, Integer studentId) {
        return tStudentRepository.findTStudentByClassroomIdAndStudentId(classroomId, studentId);
    }

    @Override
    public void savePassStudent(TStudentTemp tStudentTemp) {

        TStudent tStudent = new TStudent();

        tStudent.setStudentId(tStudentTemp.getStudentId());
        tStudent.setClassroomId(tStudentTemp.getClassroomId());

        tStudentRepository.save(tStudent);
    }
}

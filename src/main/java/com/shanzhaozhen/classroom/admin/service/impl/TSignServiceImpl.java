package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.repository.TSignRepository;
import com.shanzhaozhen.classroom.admin.service.SysUserService;
import com.shanzhaozhen.classroom.admin.service.TSignService;
import com.shanzhaozhen.classroom.admin.service.TSignTaskService;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.TSign;
import com.shanzhaozhen.classroom.bean.TSignTask;
import com.shanzhaozhen.classroom.common.CommonConst;
import com.shanzhaozhen.classroom.param.ExcelParam;
import com.shanzhaozhen.classroom.utils.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TSignServiceImpl implements TSignService {

    @Autowired
    private TSignRepository tSignRepository;

    @Autowired
    private TSignTaskService tSignTaskService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FaceServiceProvider faceServiceProvider;

    @Autowired
    private FileServiceProvider fileServiceProvider;

    @Override
    public Page<TSign> getTSignPage(Integer signTaskId, String keyword, Pageable pageable) {
        keyword = "%" + keyword + "%";
        Page<TSign> page = tSignRepository.findTSignsBySignTaskIdAndKeyword(signTaskId, keyword, keyword, pageable);
        return page;
    }

    @Override
    public Map<String, Object> sign(TSign tSign) {
        Map<String, Object> map = new HashMap<>();
        if (tSign.getSignTaskId() == null) {
            map.put("success", false);
            map.put("msg", "没有找到相关的考勤任务");
            return map;
        }

        TSignTask tSignTask = tSignTaskService.getTSignTaskById(tSign.getSignTaskId());
        if (tSignTask == null) {
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

        TSign temp = tSignRepository.findTSignByCreaterIdAndSignTaskId(sysUser.getId(), tSign.getSignTaskId());
        if (temp != null) {
            map.put("success", false);
            map.put("msg", "请勿重复签到");
            return map;
        }

        Date now = new Date();

        if (now.before(tSignTask.getStartDate())) {
            map.put("success", false);
            map.put("msg", "考勤还没开始");
            return map;
        }

        if (now.after(tSignTask.getEndDate())) {
            map.put("success", false);
            map.put("msg", "考勤已经结束");
            return map;
        }

        List<Integer> signTypeList = Arrays.asList(tSignTask.getSignType());

        if (signTypeList.contains(CommonConst.SignType.LOCATION.getValue())) {
            if (tSignTask.getLongitude() == null || tSignTask.getLatitude() == null || tSign.getLongitude() == null || tSign.getLatitude() == null) {
                map.put("success", false);
                map.put("msg", "位置获取失败");
                return map;
            }

            double distance = LocationUtils.distanceByLongNLat(tSignTask.getLongitude(), tSignTask.getLatitude(),
                    tSign.getLongitude(), tSign.getLatitude());
            if (distance > tSignTask.getScope()) {
                map.put("success", false);
                map.put("msg", "不在签到的范围内");
                return map;
            }
        }

        if (signTypeList.contains(CommonConst.SignType.FACEDETECT.getValue())) {
            if (StringUtils.isEmpty(sysUser.getFaceToken())) {
                map.put("success", false);
                map.put("msg", "未录入脸谱");
                return map;
            }

            if (StringUtils.isEmpty(tSign.getFaceToken())) {
                map.put("success", false);
                map.put("msg", "未识别脸谱");
                return map;
            }

            double confidence = faceServiceProvider.compareFaceResultConfidence(sysUser.getFaceToken(), tSign.getFaceToken());

            if (confidence < 80) {
                map.put("success", false);
                map.put("msg", "人脸比对不正确");
                return map;
            }

        }

        tSign.setCreaterId(sysUser.getId());
        tSignRepository.save(tSign);
        map.put("success", true);
        map.put("msg", "签到成功");
        return map;
    }

    @Override
    public Map<String, Object> getTSignBySignTaskId(Integer signTaskId) {

        Map<String, Object> map = new HashMap<>();

        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            map.put("success", false);
            map.put("msg", "获取失败");
            return map;
        }

        TSign tSign = tSignRepository.findTSignByCreaterIdAndSignTaskId(sysUser.getId(), signTaskId);

        if (tSign == null) {
            map.put("success", false);
            map.put("msg", "未签到");
            return map;
        }

        map.put("success", true);
        map.put("msg", "已签到");
        map.put("data", tSign);
        return map;
    }

    @Override
    public void exportSignDataBySignTaskId(Integer signTaskId, HttpServletResponse httpServletResponse) {
        TSignTask tSignTask = tSignTaskService.getTSignTaskById(signTaskId);

        if (tSignTask == null) {
            return;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<TSign> list = tSignRepository.findTSignsBySignTaskId(signTaskId);
        String[] rowName = new String[] {"序号", "学号", "姓名", "出勤时间"};
        List<String> rowNameList = Arrays.asList(rowName);
        String footer = null;

        List<List<Object>> dataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<Object> item = new ArrayList<>();
            TSign tSign = list.get(i);
            item.add("" + (i + 1));
            item.add(tSign.getNumber());
            item.add(tSign.getFullName());
            if (tSign.getCreatedDate() == null) {
                item.add("(未签到)");
            } else {
                item.add(simpleDateFormat.format(tSign.getCreatedDate()));
            }
            dataList.add(item);
        }
        if (list.size() > 0) {
            Map<String, Object> result = tSignTaskService.getAttendanceRateBySignTaskId(signTaskId);
            if ((boolean) result.get("success") == true) {
                footer = "班级人数：" + result.get("studentNumber") + "，出勤人数：" + result.get("attendanceNumber") +  "，出勤率：" + result.get("attendanceRate");
            }
        }
        Workbook workbook = PoiUtils.writeExcel(new ExcelParam(null, "“" + tSignTask.getName() + "”出勤数据", rowNameList, dataList, footer));
        PoiUtils.exportExcel(httpServletResponse, workbook);
    }

}

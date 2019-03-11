package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.repository.THomeworkRepository;
import com.shanzhaozhen.classroom.admin.service.THomeworkService;
import com.shanzhaozhen.classroom.bean.THomework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class THomeworkServiceImpl implements THomeworkService {

    @Autowired
    private THomeworkRepository tHomeworkRepository;


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

}

package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.repository.TSignInRepository;
import com.shanzhaozhen.classroom.admin.service.TSignInService;
import com.shanzhaozhen.classroom.bean.TSignIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TSignInServiceImpl implements TSignInService {

    @Autowired
    private TSignInRepository tSignInRepository;

    @Override
    public Page<TSignIn> getTSignInPage(Integer signInTaskId, String keyword, Pageable pageable) {
        keyword = "%" + keyword + "%";
        Page<TSignIn> page = tSignInRepository.findTSignInsBySignInTaskIdAndKeyword(signInTaskId, keyword, keyword, pageable);
        return page;
    }

}

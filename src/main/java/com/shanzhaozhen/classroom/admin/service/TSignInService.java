package com.shanzhaozhen.classroom.admin.service;

import com.shanzhaozhen.classroom.bean.TSignIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface TSignInService {

    Page<TSignIn> getTSignInPage(Integer signInTaskId, String keyword, Pageable pageable);

    Map<String, Object> signIn(TSignIn tSignIn);
}

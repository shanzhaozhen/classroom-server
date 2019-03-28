package com.shanzhaozhen.classroom.admin.service;

import com.shanzhaozhen.classroom.bean.TSignIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface TSignInService {

    Page<TSignIn> getTSignInPage(Integer signInTaskId, String keyword, Pageable pageable);

    Map<String, Object> signIn(TSignIn tSignIn);

    Map<String, Object> getTSignInBySignInTaskId(Integer signInTaskId);
}

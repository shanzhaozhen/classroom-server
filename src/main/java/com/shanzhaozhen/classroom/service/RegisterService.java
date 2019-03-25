package com.shanzhaozhen.classroom.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface RegisterService {

    Map<String, Object> RegisterNewUser(JSONObject jsonParam);

    Map<String, Boolean> checkUsername(String username);

}

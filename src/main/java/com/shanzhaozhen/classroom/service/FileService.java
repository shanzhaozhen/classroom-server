package com.shanzhaozhen.classroom.service;


import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileService {

    Map<String, Object> upload(MultipartFile multipartFile);

}

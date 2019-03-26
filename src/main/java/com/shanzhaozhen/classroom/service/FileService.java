package com.shanzhaozhen.classroom.service;


import com.shanzhaozhen.classroom.bean.TFileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileService {

    Map<String, Object> upload(MultipartFile multipartFile);


    Map<String, Object> saveTFileInfo(TFileInfo tFileInfo);

}

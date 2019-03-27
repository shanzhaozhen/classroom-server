package com.shanzhaozhen.classroom.service;


import com.shanzhaozhen.classroom.bean.TFileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface FileService {

    Map<String, Object> upload(MultipartFile multipartFile, String fileName);


    Map<String, Object> saveTFileInfo(TFileInfo tFileInfo);

    void download(Integer id, HttpServletResponse httpServletResponse) throws IOException;
}

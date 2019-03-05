package com.shanzhaozhen.classroom.controller;

import com.shanzhaozhen.classroom.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${upload.relativePath}")
    private String relativePath;

    @Value("${upload.realPath}")
    private String realPath;

    @RequestMapping("upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") MultipartFile multipartFile) {
        return FileUtils.saveFile(multipartFile, relativePath, realPath);
    }

}

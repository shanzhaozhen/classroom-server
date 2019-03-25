package com.shanzhaozhen.classroom.controller;

import com.shanzhaozhen.classroom.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile multipartFile) {
        return fileService.upload(multipartFile);
    }

}

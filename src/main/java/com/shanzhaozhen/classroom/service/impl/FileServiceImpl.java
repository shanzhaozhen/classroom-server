package com.shanzhaozhen.classroom.service.impl;

import com.shanzhaozhen.classroom.utils.FileServiceProvider;
import com.shanzhaozhen.classroom.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileServiceProvider fileServiceProvider;

    @Override
    public Map<String, Object> upload(MultipartFile multipartFile) {
        return fileServiceProvider.saveFile(multipartFile);
    }

}

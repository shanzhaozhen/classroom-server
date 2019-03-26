package com.shanzhaozhen.classroom.service.impl;

import com.shanzhaozhen.classroom.admin.repository.TFileInfoRepository;
import com.shanzhaozhen.classroom.bean.TFileInfo;
import com.shanzhaozhen.classroom.utils.FileServiceProvider;
import com.shanzhaozhen.classroom.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileServiceProvider fileServiceProvider;

    @Autowired
    private TFileInfoRepository tFileInfoRepository;

    @Override
    public Map<String, Object> upload(MultipartFile multipartFile) {
        return fileServiceProvider.saveFile(multipartFile);
    }

    @Override
    public Map<String, Object> saveTFileInfo(TFileInfo tFileInfo) {
        Map<String, Object> map = new HashMap<>();
        tFileInfoRepository.save(tFileInfo);
        map.put("success", true);
        map.put("msg", "文件保存成功");
        return map;
    }


}

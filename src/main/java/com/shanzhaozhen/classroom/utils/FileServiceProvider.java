package com.shanzhaozhen.classroom.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class FileServiceProvider {

    @Value("${upload.relativePath}")
    private String relativePath;

    @Value("${upload.realPath}")
    private String realPath;

    public Map<String, Object> saveFile(MultipartFile multipartFile) {

        Map<String, Object> map = new HashMap<>();

        if (multipartFile.isEmpty()) {
            map.put("success", false);
            map.put("msg", "文件为空！");
        }

        String fileName = multipartFile.getOriginalFilename();

        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        String newPath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String savePath = realPath + newPath;

        String newFileName = UUID.randomUUID().toString() + suffixName;

        File file = new File(savePath + "/" +  newFileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            multipartFile.transferTo(file);
            map.put("success", true);
            map.put("msg", "文件上传成功！");
            map.put("relativePath", relativePath + newPath + "/" + newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "文件上传失败！");
            return map;
        }
        return map;
    }

}

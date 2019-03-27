package com.shanzhaozhen.classroom.utils;

import com.shanzhaozhen.classroom.bean.TFileInfo;
import com.shanzhaozhen.classroom.service.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    @Autowired
    private FileService fileService;

    public Map<String, Object> saveFile(MultipartFile multipartFile, String fileName) {

        Map<String, Object> map = new HashMap<>();

        if (multipartFile.isEmpty()) {
            map.put("success", false);
            map.put("msg", "文件为空！");
        }

        if (StringUtils.isEmpty(fileName)) {
            fileName = multipartFile.getOriginalFilename();
        }

        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        String newPath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String savePath = realPath + newPath;

        String newFileName = UUID.randomUUID().toString() + suffixName;

        String newRealPath = savePath + "/" +  newFileName;

        File file = new File(newRealPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        String newRelativePath = relativePath + newPath + "/" + newFileName;


        try {
            multipartFile.transferTo(file);
            TFileInfo tFileInfo = new TFileInfo(fileName, suffixName, newRelativePath, newRealPath, null, null);
            fileService.saveTFileInfo(tFileInfo);
            if (tFileInfo.getId() == null) {
                map.put("success", false);
                map.put("msg", "文件上传失败！");
                return map;
            }
            map.put("success", true);
            map.put("msg", "文件上传成功！");
            map.put("fileInfoId", tFileInfo.getId());
        } catch (IOException e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "文件上传失败！");
            return map;
        }
        return map;
    }

    public void downloadFile(TFileInfo tFileInfo, HttpServletResponse httpServletResponse) throws IOException {

        File file = new File(tFileInfo.getRealPath());

        if (!file.exists()) {
//            httpServletResponse.getWriter().write("File Not Found");
            return;
        }
        try(
            FileInputStream fileInputStream = new FileInputStream(file)
        ) {
            // 设置以流的形式下载文件，这样可以实现任意格式的文件下载
            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.addHeader("Content-Disposition", " attachment;filename=" + tFileInfo.getFileName());
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();            //请求完成servlet会自动关闭
            IOUtils.copy(fileInputStream, servletOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

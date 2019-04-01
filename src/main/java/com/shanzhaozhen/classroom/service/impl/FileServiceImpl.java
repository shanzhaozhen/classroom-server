package com.shanzhaozhen.classroom.service.impl;

import com.shanzhaozhen.classroom.admin.repository.TFileInfoRepository;
import com.shanzhaozhen.classroom.admin.service.SysUserService;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.bean.TFileInfo;
import com.shanzhaozhen.classroom.utils.FileServiceProvider;
import com.shanzhaozhen.classroom.service.FileService;
import com.shanzhaozhen.classroom.utils.UserDetailsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FileServiceProvider fileServiceProvider;

    @Autowired
    private TFileInfoRepository tFileInfoRepository;

    @Override
    public Map<String, Object> upload(MultipartFile multipartFile, String fileName) {
        return fileServiceProvider.saveFile(multipartFile, fileName);
    }

    @Override
    public Map<String, Object> saveTFileInfo(TFileInfo tFileInfo) {
        Map<String, Object> map = new HashMap<>();
        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            tFileInfo.setCreaterId(sysUser.getId());
        }

        tFileInfoRepository.save(tFileInfo);
        map.put("success", true);
        map.put("msg", "文件保存成功");
        return map;
    }

    @Override
    public void download(Integer id, HttpServletResponse httpServletResponse) throws IOException {
        TFileInfo tFileInfo = tFileInfoRepository.findTFileInfoById(id);
        if (tFileInfo == null) {
//            httpServletResponse.getWriter().write("File Not Found");
            return;
        }

        fileServiceProvider.downloadFile(tFileInfo, httpServletResponse);

    }


}

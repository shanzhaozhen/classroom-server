package com.shanzhaozhen.classroom.admin.repository;

import com.shanzhaozhen.classroom.bean.TFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TFileInfoRepository extends JpaRepository<TFileInfo, Integer> {

    TFileInfo findTFileInfoById(Integer id);

}

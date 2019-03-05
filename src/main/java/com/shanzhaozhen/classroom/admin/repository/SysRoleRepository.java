package com.shanzhaozhen.classroom.admin.repository;

import com.shanzhaozhen.classroom.bean.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleRepository extends JpaRepository<SysRole, Integer> {

    SysRole findSysRoleById(Integer id);

//    List<SysRole> findByUserName(String username);

}

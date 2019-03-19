package com.shanzhaozhen.classroom.admin.repository;

import com.shanzhaozhen.classroom.bean.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserRepository extends JpaRepository<SysUser, Integer> {

    SysUser findSysUserById(Integer id);

    SysUser findSysUserByUsername(String username);

    int countByUsername(String username);

    SysUser findSysUserByOpenId(String openId);
}

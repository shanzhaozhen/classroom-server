package com.shanzhaozhen.classroom.admin.repository;

import com.shanzhaozhen.classroom.bean.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysPermissionRepository extends JpaRepository<SysPermission, Integer> {

    SysPermission findSysPermissionsById(Integer id);

    List<SysPermission> findAll();

    List<SysPermission> findSysPermissionsByPermissionNameIsNotNull();

    @Query(value = "select p.* " +
            "from sys_user u " +
            "inner join sys_users_roles sur on u.id = sur.user_id " +
            "inner join sys_role r on r.id = sur.role_id " +
            "inner join sys_roles_permissions srp on r.id = srp.role_id " +
            "inner join sys_permission p on p.id = srp.permission_id " +
            "where u.username = ?1", nativeQuery = true)
    List<SysPermission> findByUsername(String username);

//    @Query()
//    public Set<SysPermission> findBySysRoleId(Integer roleId);

}

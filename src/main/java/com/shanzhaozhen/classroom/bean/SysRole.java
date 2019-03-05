package com.shanzhaozhen.classroom.bean;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sys_role")
public class SysRole extends BaseBean {

    private static final long serialVersionUID = -232119212010132125L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

//    @ManyToMany(mappedBy = "sysRoles", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_users_roles",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private Set<SysUser> sysUsers;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_roles_permissions",
        joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")}
    )
    private Set<SysPermission> sysPermissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SysUser> getSysUsers() {
        return sysUsers;
    }

    public void setSysUsers(Set<SysUser> sysUsers) {
        this.sysUsers = sysUsers;
    }

    public Set<SysPermission> getSysPermissions() {
        return sysPermissions;
    }

    public void setSysPermissions(Set<SysPermission> sysPermissions) {
        this.sysPermissions = sysPermissions;
    }

}

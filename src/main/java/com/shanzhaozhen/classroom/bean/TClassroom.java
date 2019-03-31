package com.shanzhaozhen.classroom.bean;

import javax.persistence.*;

@Entity
@Table(name = "t_classroom")
public class TClassroom extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //班级名称
    @Column(nullable = false)
    private String name;

    //班级概述
    private String outline;

    //创建人id
    @Column(nullable = false)
    private Integer createrId;

    //发布状态
    private boolean announce = false;

    //班级人数
    @Transient
    private Long studentNumber;

    //创建人信息
    @Transient
    private SysUserInfo createrInfo;

    //加入状态
    @Transient
    private boolean joinState;

    public TClassroom() {
    }

    public TClassroom(String name, String outline, Integer createrId, boolean announce, Long studentNumber, SysUserInfo createrInfo, boolean joinState) {
        this.name = name;
        this.outline = outline;
        this.createrId = createrId;
        this.announce = announce;
        this.studentNumber = studentNumber;
        this.createrInfo = createrInfo;
        this.joinState = joinState;
    }

    public TClassroom(TClassroom tClassroom, SysUserInfo createrInfo) {
        this.id = tClassroom.getId();
        this.name = tClassroom.getName();
        this.outline = tClassroom.getOutline();
        this.createrId = tClassroom.getCreaterId();
        this.announce = tClassroom.isAnnounce();
        super.setCreatedDate(tClassroom.getCreatedDate());
        super.setLastModifiedDate(tClassroom.getLastModifiedDate());
        this.createrInfo = createrInfo;
    }

    public TClassroom(TClassroom tClassroom, SysUserInfo createrInfo, Integer studentId) {
        this.id = tClassroom.getId();
        this.name = tClassroom.getName();
        this.outline = tClassroom.getOutline();
        this.createrId = tClassroom.getCreaterId();
        this.announce = tClassroom.isAnnounce();
        super.setCreatedDate(tClassroom.getCreatedDate());
        super.setLastModifiedDate(tClassroom.getLastModifiedDate());
        this.createrInfo = createrInfo;
        if (studentId != null) {
            this.joinState = true;
        } else {
            this.joinState = false;
        }
    }

    public TClassroom(TClassroom tClassroom, Long studentNumber) {
        this.id = tClassroom.getId();
        this.name = tClassroom.getName();
        this.outline = tClassroom.getOutline();
        this.createrId = tClassroom.getCreaterId();
        this.announce = tClassroom.isAnnounce();
        super.setCreatedDate(tClassroom.getCreatedDate());
        super.setLastModifiedDate(tClassroom.getLastModifiedDate());
        this.studentNumber = studentNumber;
    }

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

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    public boolean isAnnounce() {
        return announce;
    }

    public void setAnnounce(boolean announce) {
        this.announce = announce;
    }

    public Long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public SysUserInfo getCreaterInfo() {
        return createrInfo;
    }

    public void setCreaterInfo(SysUserInfo createrInfo) {
        this.createrInfo = createrInfo;
    }

    public boolean isJoinState() {
        return joinState;
    }

    public void setJoinState(boolean joinState) {
        this.joinState = joinState;
    }
}

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

    //班级类型
    private Integer classType;

    //老师的id/创建人id
    @Column(nullable = false)
    private Integer headmasterId;

    //班长的id
    private Integer monitorId;

    //发布状态
    private boolean announce = false;

    //班级人数
    @Transient
    private Long studentNumber;

    //创建人信息
    @Transient
    private SysUserInfo headmasterInfo;

    public TClassroom() {
    }

    public TClassroom(String name, String outline, Integer classType, Integer headmasterId, Integer monitorId, boolean announce, SysUserInfo headmasterInfo, Long studentNumber) {
        this.name = name;
        this.outline = outline;
        this.classType = classType;
        this.headmasterId = headmasterId;
        this.monitorId = monitorId;
        this.announce = announce;
        this.headmasterInfo = headmasterInfo;
        this.studentNumber = studentNumber;
    }

//    public TClassroom(TClassroom tClassroom, String headmasterName, Long studentNumber) {
//        this.id = tClassroom.getId();
//        this.name = tClassroom.getName();
//        this.outline = tClassroom.getOutline();
//        this.classType = tClassroom.getClassType();
//        this.headmasterId = tClassroom.getHeadmasterId();
//        this.monitorId = tClassroom.getMonitorId();
//        this.announce = tClassroom.isAnnounce();
//        super.setCreatedDate(tClassroom.getCreatedDate());
//        super.setLastModifiedDate(tClassroom.getLastModifiedDate());
//        this.studentNumber = studentNumber;
//        this.headmasterName = headmasterName;
//    }

    public TClassroom(TClassroom tClassroom, SysUserInfo headmasterInfo) {
        this.id = tClassroom.getId();
        this.name = tClassroom.getName();
        this.outline = tClassroom.getOutline();
        this.classType = tClassroom.getClassType();
        this.headmasterId = tClassroom.getHeadmasterId();
        this.monitorId = tClassroom.getMonitorId();
        this.announce = tClassroom.isAnnounce();
        super.setCreatedDate(tClassroom.getCreatedDate());
        super.setLastModifiedDate(tClassroom.getLastModifiedDate());
        this.headmasterInfo = headmasterInfo;
    }

    public TClassroom(TClassroom tClassroom, Long studentNumber) {
        this.id = tClassroom.getId();
        this.name = tClassroom.getName();
        this.outline = tClassroom.getOutline();
        this.classType = tClassroom.getClassType();
        this.headmasterId = tClassroom.getHeadmasterId();
        this.monitorId = tClassroom.getMonitorId();
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

    public Integer getClassType() {
        return classType;
    }

    public void setClassType(Integer classType) {
        this.classType = classType;
    }

    public Integer getHeadmasterId() {
        return headmasterId;
    }

    public void setHeadmasterId(Integer headmasterId) {
        this.headmasterId = headmasterId;
    }

    public Integer getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Integer monitorId) {
        this.monitorId = monitorId;
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

    public SysUserInfo getHeadmasterInfo() {
        return headmasterInfo;
    }

    public void setHeadmasterInfo(SysUserInfo headmasterInfo) {
        this.headmasterInfo = headmasterInfo;
    }
}

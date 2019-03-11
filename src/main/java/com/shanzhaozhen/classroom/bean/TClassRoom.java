package com.shanzhaozhen.classroom.bean;

import javax.persistence.*;

@Entity
@Table(name = "t_class_room")
public class TClassRoom extends BaseBean {

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

    public TClassRoom() {
    }

    public TClassRoom(String name, String outline, Integer classType, Integer headmasterId, Integer monitorId, boolean announce, Long studentNumber) {
        this.name = name;
        this.outline = outline;
        this.classType = classType;
        this.headmasterId = headmasterId;
        this.monitorId = monitorId;
        this.announce = announce;
        this.studentNumber = studentNumber;
    }

    public TClassRoom(TClassRoom tClassRoom, Long studentNumber) {
        this.id = tClassRoom.getId();
        this.name = tClassRoom.getName();
        this.outline = tClassRoom.getOutline();
        this.classType = tClassRoom.getClassType();
        this.headmasterId = tClassRoom.getHeadmasterId();
        this.monitorId = tClassRoom.getMonitorId();
        this.announce = tClassRoom.isAnnounce();
        super.setCreatedDate(tClassRoom.getCreatedDate());
        super.setLastModifiedDate(tClassRoom.getLastModifiedDate());
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
}

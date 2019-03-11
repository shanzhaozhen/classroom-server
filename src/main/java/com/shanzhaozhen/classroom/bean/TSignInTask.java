package com.shanzhaozhen.classroom.bean;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_sign_in_task")
public class TSignInTask extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //班级id
    @Column(nullable = false)
    private Integer classId;

    //创建人id
    @Column(nullable = false)
    private Integer createrId;

    //考勤任务名
    @Column(nullable = false)
    private String name;

    //签到描述
    private String outline;

    //开始时间
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date startDate;

    //结束时间
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date endDate;

    //签到方式
    private Integer signInType;

    //地址
    private String address;

    //经度
    @Column(precision=20, scale=17)
    private Double longitude;

    //纬度
    @Column(precision=20, scale=17)
    private Double latitude;

    //范围
    private Double scope;

    //发布状态
    private boolean announce = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getSignInType() {
        return signInType;
    }

    public void setSignInType(Integer signInType) {
        this.signInType = signInType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getScope() {
        return scope;
    }

    public void setScope(Double scope) {
        this.scope = scope;
    }

    public boolean isAnnounce() {
        return announce;
    }

    public void setAnnounce(boolean announce) {
        this.announce = announce;
    }
}

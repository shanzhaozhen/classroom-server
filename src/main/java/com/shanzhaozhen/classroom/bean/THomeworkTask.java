package com.shanzhaozhen.classroom.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_homework_task")
public class THomeworkTask extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //创建人id
    @Column(nullable = false)
    private Integer createrId;

    //班级id
    @Column(nullable = false)
    private Integer classId;

    //作业名
    @Column(nullable = false)
    private String name;

    //作业描述
    private String outline;

    //开始时间
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date startDate;

    //结束时间
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date endDate;

    //发布状态
    private boolean announce = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
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

    public boolean isAnnounce() {
        return announce;
    }

    public void setAnnounce(boolean announce) {
        this.announce = announce;
    }
}

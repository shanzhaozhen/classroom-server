package com.shanzhaozhen.classroom.bean;

import javax.persistence.*;

@Entity
@Table(name = "t_homework")
public class THomework extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //作业任务id
    @Column(nullable = false)
    private Integer homeworkTaskId;

    //作业提交人id
    @Column(nullable = false)
    private Integer createrId;

    //提交的内容
    private String content;

    //附件id
    private Integer fileInfoId;

    //分数
    private Integer score;

    @Transient
    private String fullName;

    @Transient
    private String number;

    @Transient
    private String nickName;

    @Transient
    private TFileInfo tFileInfo;

    @Transient
    private String homeworkTaskName;

    public THomework() {
    }

    public THomework(Integer homeworkTaskId, Integer createrId, String content, Integer fileInfoId, Integer score, String fullName, String number, String nickName, TFileInfo tFileInfo, String homeworkTaskName) {
        this.homeworkTaskId = homeworkTaskId;
        this.createrId = createrId;
        this.content = content;
        this.fileInfoId = fileInfoId;
        this.score = score;
        this.fullName = fullName;
        this.number = number;
        this.nickName = nickName;
        this.tFileInfo = tFileInfo;
        this.homeworkTaskName = homeworkTaskName;
    }

    public THomework(THomework tHomework, String fullName, String number, String nickName) {
        if (tHomework != null) {
            this.id = tHomework.getId();
            this.homeworkTaskId = tHomework.getHomeworkTaskId();
            this.createrId = tHomework.getCreaterId();
            this.content = tHomework.getContent();
            this.fileInfoId = tHomework.getFileInfoId();
            this.score = tHomework.getScore();
            super.setCreatedDate(tHomework.getCreatedDate());
            super.setLastModifiedDate(tHomework.getLastModifiedDate());
        }
        this.fullName = fullName;
        this.number = number;
        this.nickName = nickName;
    }

    public THomework(THomework tHomework, String fullName, String number, String nickName, TFileInfo tFileInfo) {
        this.id = tHomework.getId();
        this.homeworkTaskId = tHomework.getHomeworkTaskId();
        this.createrId = tHomework.getCreaterId();
        this.content = tHomework.getContent();
        this.fileInfoId = tHomework.getFileInfoId();
        this.score = tHomework.getScore();
        super.setCreatedDate(tHomework.getCreatedDate());
        super.setLastModifiedDate(tHomework.getLastModifiedDate());
        this.fullName = fullName;
        this.number = number;
        this.nickName = nickName;
        this.tFileInfo = tFileInfo;
    }

    public THomework(THomework tHomework, String fullName, String number, String nickName, String homeworkTaskName) {
        this.id = tHomework.getId();
        this.homeworkTaskId = tHomework.getHomeworkTaskId();
        this.createrId = tHomework.getCreaterId();
        this.content = tHomework.getContent();
        this.fileInfoId = tHomework.getFileInfoId();
        this.score = tHomework.getScore();
        super.setCreatedDate(tHomework.getCreatedDate());
        super.setLastModifiedDate(tHomework.getLastModifiedDate());
        this.fullName = fullName;
        this.number = number;
        this.nickName = nickName;
        this.homeworkTaskName = homeworkTaskName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHomeworkTaskId() {
        return homeworkTaskId;
    }

    public void setHomeworkTaskId(Integer homeworkTaskId) {
        this.homeworkTaskId = homeworkTaskId;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFileInfoId() {
        return fileInfoId;
    }

    public void setFileInfoId(Integer fileInfoId) {
        this.fileInfoId = fileInfoId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public TFileInfo gettFileInfo() {
        return tFileInfo;
    }

    public void settFileInfo(TFileInfo tFileInfo) {
        this.tFileInfo = tFileInfo;
    }

    public String getHomeworkTaskName() {
        return homeworkTaskName;
    }

    public void setHomeworkTaskName(String homeworkTaskName) {
        this.homeworkTaskName = homeworkTaskName;
    }
}

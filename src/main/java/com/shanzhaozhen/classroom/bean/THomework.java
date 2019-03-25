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

    //作业路径
    private String fileUrl;

    //作业附件文件名
    private String fileName;

    //分数
    private Integer score;

    @Transient
    private String fullName;

    @Transient
    private String number;

    @Transient
    private String nickname;

    public THomework() {
    }

    public THomework(Integer homeworkTaskId, Integer createrId, String content, String fileUrl, String fileName, Integer score, String fullName, String number, String nickname) {
        this.homeworkTaskId = homeworkTaskId;
        this.createrId = createrId;
        this.content = content;
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.score = score;
        this.fullName = fullName;
        this.number = number;
        this.nickname = nickname;
    }

    public THomework(THomework tHomework, String fullName, String number, String nickname) {
        if (tHomework != null) {
            this.id = tHomework.getId();
            this.homeworkTaskId = tHomework.getHomeworkTaskId();
            this.createrId = tHomework.getCreaterId();
            this.content = tHomework.getContent();
            this.fileUrl = tHomework.getFileUrl();
            this.fileName = tHomework.getFileName();
            this.score = tHomework.getScore();
            super.setCreatedDate(tHomework.getCreatedDate());
            super.setLastModifiedDate(tHomework.getLastModifiedDate());
        }
        this.fullName = fullName;
        this.number = number;
        this.nickname = nickname;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickName) {
        this.nickname = nickName;
    }
}

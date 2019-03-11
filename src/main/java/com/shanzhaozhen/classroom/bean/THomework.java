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
    private Integer homewordTaskId;

    //作业提交人id
    @Column(nullable = false)
    private Integer createrId;

    //提交的内容
    private String content;

    //分数
    private Integer score;

    @Transient
    private String fullName;

    @Transient
    private String nickname;

    public THomework() {
    }

    public THomework(Integer homewordTaskId, Integer createrId, String content, Integer score, String fullName, String nickname) {
        this.homewordTaskId = homewordTaskId;
        this.createrId = createrId;
        this.content = content;
        this.score = score;
        this.fullName = fullName;
        this.nickname = nickname;
    }

    public THomework(THomework tHomework, String fullName, String nickname) {
        if (tHomework != null) {
            this.id = tHomework.getId();
            this.homewordTaskId = tHomework.getHomewordTaskId();
            this.createrId = tHomework.getCreaterId();
            this.content = tHomework.getContent();
            this.score = tHomework.getScore();
            super.setCreatedDate(tHomework.getCreatedDate());
            super.setLastModifiedDate(tHomework.getLastModifiedDate());
        }
        this.fullName = fullName;
        this.nickname = nickname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHomewordTaskId() {
        return homewordTaskId;
    }

    public void setHomewordTaskId(Integer homewordTaskId) {
        this.homewordTaskId = homewordTaskId;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickName) {
        this.nickname = nickName;
    }
}

package com.shanzhaozhen.classroom.bean;

import javax.persistence.*;

@Entity
@Table(name = "t_sign_in")
public class TSignIn extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer signInTaskId;

    @Column(nullable = false)
    private Integer createrId;

    //经度
    @Column(precision=20, scale=17)
    private Double longitude;

    //维度
    @Column(precision=20, scale=17)
    private Double latitude;

    @Transient
    private String fullName;

    @Transient
    private String number;

    @Transient
    private String nickname;

    public TSignIn() {
    }

    public TSignIn(Integer signInTaskId, Integer createrId, Double longitude, Double latitude, String fullName, String number, String nickname) {
        this.signInTaskId = signInTaskId;
        this.createrId = createrId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.fullName = fullName;
        this.number = number;
        this.nickname = nickname;
    }

    public TSignIn(TSignIn tSignIn, String fullName, String number, String nickname) {
        if (tSignIn != null) {
            this.id = tSignIn.getId();
            this.signInTaskId = tSignIn.getSignInTaskId();
            this.createrId = tSignIn.getCreaterId();
            this.longitude = tSignIn.getLongitude();
            this.latitude = tSignIn.getLatitude();
            super.setCreatedDate(tSignIn.getCreatedDate());
            super.setLastModifiedDate(tSignIn.getLastModifiedDate());
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

    public Integer getSignInTaskId() {
        return signInTaskId;
    }

    public void setSignInTaskId(Integer signInTaskId) {
        this.signInTaskId = signInTaskId;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

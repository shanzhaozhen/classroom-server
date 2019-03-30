package com.shanzhaozhen.classroom.bean;

import javax.persistence.*;

@Entity
@Table(name = "t_sign")
public class TSign extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer signTaskId;

    @Column(nullable = false)
    private Integer createrId;

    //经度
    @Column(precision=20, scale=17)
    private Double longitude;

    //维度
    @Column(precision=20, scale=17)
    private Double latitude;

    private String faceToken;

    @Transient
    private String fullName;

    @Transient
    private String number;

    @Transient
    private String nickName;

    public TSign() {
    }

    public TSign(Integer signTaskId, Integer createrId, Double longitude, Double latitude, String faceToken, String fullName, String number, String nickName) {
        this.signTaskId = signTaskId;
        this.createrId = createrId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.faceToken = faceToken;
        this.fullName = fullName;
        this.number = number;
        this.nickName = nickName;
    }

    public TSign(TSign tSign, String fullName, String number, String nickName) {
        if (tSign != null) {
            this.id = tSign.getId();
            this.signTaskId = tSign.getSignTaskId();
            this.createrId = tSign.getCreaterId();
            this.longitude = tSign.getLongitude();
            this.latitude = tSign.getLatitude();
            this.faceToken = tSign.getFaceToken();
            super.setCreatedDate(tSign.getCreatedDate());
            super.setLastModifiedDate(tSign.getLastModifiedDate());
        }
        this.fullName = fullName;
        this.number = number;
        this.nickName = nickName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(Integer signTaskId) {
        this.signTaskId = signTaskId;
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

    public String getFaceToken() {
        return faceToken;
    }

    public void setFaceToken(String faceToken) {
        this.faceToken = faceToken;
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
}

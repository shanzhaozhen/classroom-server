package com.shanzhaozhen.classroom.bean;

import javax.persistence.*;

@Entity
@Table(name = "t_student_temp")
public class TStudentTemp extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //学生id
    @Column(nullable = false)
    private Integer studentId;

    //班级id
    @Column(nullable = false)
    private Integer classroomId;

    @Transient
    private String number;

    @Transient
    private String fullName;

    @Transient
    private String nickName;

    @Transient
    private String classroomName;

    public TStudentTemp() {
    }

    public TStudentTemp(Integer studentId, Integer classroomId, String number, String fullName, String nickName, String classroomName) {
        this.studentId = studentId;
        this.classroomId = classroomId;
        this.number = number;
        this.fullName = fullName;
        this.nickName = nickName;
        this.classroomName = classroomName;
    }

    public TStudentTemp(TStudentTemp tStudentTemp, String number, String fullName, String nickName, String classroomName) {
        this.id = tStudentTemp.getId();
        this.studentId = tStudentTemp.getStudentId();
        this.classroomId = tStudentTemp.getClassroomId();
        super.setCreatedDate(tStudentTemp.getCreatedDate());
        super.setLastModifiedDate(tStudentTemp.getLastModifiedDate());
        this.number = number;
        this.fullName = fullName;
        this.nickName = nickName;
        this.classroomName = classroomName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }
}

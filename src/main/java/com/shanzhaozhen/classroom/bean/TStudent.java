package com.shanzhaozhen.classroom.bean;

import javax.persistence.*;

@Entity
@Table(name = "t_student")
public class TStudent extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //学生id
    @Column(nullable = false)
    private Integer studentId;

    //班级id
    @Column(nullable = false)
    private Integer classId;

    @Transient
    private String fullName;

    @Transient
    private String nickname;

    public TStudent() {
    }

    public TStudent(Integer studentId, Integer classId, String fullName, String nickname) {
        this.studentId = studentId;
        this.classId = classId;
        this.fullName = fullName;
        this.nickname = nickname;
    }

    public TStudent(TStudent tStudent, String fullName, String nickname) {
        this.id = tStudent.getId();
        this.studentId = tStudent.getStudentId();
        this.classId = tStudent.getClassId();
        super.setCreatedDate(tStudent.getCreatedDate());
        super.setLastModifiedDate(tStudent.getLastModifiedDate());
        this.fullName = fullName;
        this.nickname = nickname;
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

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

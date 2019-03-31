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
    private Integer classroomId;

    @Transient
    private String number;

    @Transient
    private String fullName;

    @Transient
    private String nickName;

    //提交数
    @Transient
    private int commitNumber;

    //总作业任务数
    @Transient
    private int totalCommitNumber;

    //提交率
    @Transient
    private String commitRate;

    //作业平均分
    @Transient
    private float averageScore;

    //出勤次数
    @Transient
    private int attendanceNumber;

    //总出勤任务数
    @Transient
    private int totalAttendanceNumber;

    //出勤率
    @Transient
    private String attendanceRate;

    public TStudent() {
    }

    public TStudent(Integer studentId, Integer classroomId, String number, String fullName, String nickName, int commitNumber, int totalCommitNumber, String commitRate, float averageScore, int attendanceNumber, int totalAttendanceNumber, String attendanceRate) {
        this.studentId = studentId;
        this.classroomId = classroomId;
        this.number = number;
        this.fullName = fullName;
        this.nickName = nickName;
        this.commitNumber = commitNumber;
        this.totalCommitNumber = totalCommitNumber;
        this.commitRate = commitRate;
        this.averageScore = averageScore;
        this.attendanceNumber = attendanceNumber;
        this.totalAttendanceNumber = totalAttendanceNumber;
        this.attendanceRate = attendanceRate;
    }

    public TStudent(TStudent tStudent, String number, String fullName, String nickName) {
        this.id = tStudent.getId();
        this.studentId = tStudent.getStudentId();
        this.classroomId = tStudent.getClassroomId();
        super.setCreatedDate(tStudent.getCreatedDate());
        super.setLastModifiedDate(tStudent.getLastModifiedDate());
        this.number = number;
        this.fullName = fullName;
        this.nickName = nickName;
    }


    public TStudent(TStudent tStudent, String number, String fullName, String nickName, int commitNumber, int totalCommitNumber, String commitRate, float averageScore, int attendanceNumber, int totalAttendanceNumber, String attendanceRate) {
        this.id = tStudent.getId();
        this.studentId = tStudent.getStudentId();
        this.classroomId = tStudent.getClassroomId();
        super.setCreatedDate(tStudent.getCreatedDate());
        super.setLastModifiedDate(tStudent.getLastModifiedDate());
        this.number = number;
        this.fullName = fullName;
        this.nickName = nickName;
        this.commitNumber = commitNumber;
        this.totalCommitNumber = totalCommitNumber;
        this.commitRate = commitRate;
        this.averageScore = averageScore;
        this.attendanceNumber = attendanceNumber;
        this.totalAttendanceNumber = totalAttendanceNumber;
        this.attendanceRate = attendanceRate;
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

    public int getCommitNumber() {
        return commitNumber;
    }

    public void setCommitNumber(int commitNumber) {
        this.commitNumber = commitNumber;
    }

    public int getTotalCommitNumber() {
        return totalCommitNumber;
    }

    public void setTotalCommitNumber(int totalCommitNumber) {
        this.totalCommitNumber = totalCommitNumber;
    }

    public String getCommitRate() {
        return commitRate;
    }

    public void setCommitRate(String commitRate) {
        this.commitRate = commitRate;
    }

    public float getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(float averageScore) {
        this.averageScore = averageScore;
    }

    public int getAttendanceNumber() {
        return attendanceNumber;
    }

    public void setAttendanceNumber(int attendanceNumber) {
        this.attendanceNumber = attendanceNumber;
    }

    public int getTotalAttendanceNumber() {
        return totalAttendanceNumber;
    }

    public void setTotalAttendanceNumber(int totalAttendanceNumber) {
        this.totalAttendanceNumber = totalAttendanceNumber;
    }

    public String getAttendanceRate() {
        return attendanceRate;
    }

    public void setAttendanceRate(String attendanceRate) {
        this.attendanceRate = attendanceRate;
    }
}

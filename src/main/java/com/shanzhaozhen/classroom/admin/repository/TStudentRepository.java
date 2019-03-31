package com.shanzhaozhen.classroom.admin.repository;

import com.shanzhaozhen.classroom.bean.TStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TStudentRepository extends JpaRepository<TStudent, Integer> {

    TStudent findTStudentById(Integer id);

    TStudent findTStudentByClassroomIdAndStudentId(Integer classroomId, Integer studentId);

    @Query("select new TStudent(s, u.sysUserInfo.number, u.sysUserInfo.fullName, u.sysUserInfo.nickName) " +
            "from TStudent s " +
            "left join SysUser u on s.studentId = u.id " +
            "where s.classroomId = ?1 and (u.sysUserInfo.fullName like ?2 or u.sysUserInfo.nickName like ?3)")
    Page<TStudent> findPageTStudentsByClassroomIdAndKeyword(Integer classroomId, String keyword1, String keyword2, Pageable pageable);

    int deleteByClassroomId(Integer classroomId);

    @Query("select new TStudent(s, u.sysUserInfo.number, u.sysUserInfo.fullName, u.sysUserInfo.nickName) " +
            "from TStudent s " +
            "left join SysUser u on s.studentId = u.id " +
            "where s.classroomId = ?1")
    List<TStudent> findListTStudentsByClassroomId(Integer classroomId);
}

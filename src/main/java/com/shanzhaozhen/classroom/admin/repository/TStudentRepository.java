package com.shanzhaozhen.classroom.admin.repository;

import com.shanzhaozhen.classroom.bean.TStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TStudentRepository extends JpaRepository<TStudent, Integer> {

    TStudent findTStudentById(Integer id);

    TStudent findTStudentByClassIdAndStudentId(Integer classId, Integer studentId);

    @Query("select new TStudent(s, u.sysUserInfo.fullName as fullName, u.sysUserInfo.nickname as nickname) " +
            "from TStudent s " +
            "left join SysUser u on s.studentId = u.id " +
            "where s.classId = ?1 and (u.sysUserInfo.fullName like ?2 or u.sysUserInfo.nickname like ?3)")
    Page<TStudent> findPageTStudentsByClassIdAndKeyword(Integer classId, String keyword1, String keyword2, Pageable pageable);

    int deleteByClassId(Integer classId);
}

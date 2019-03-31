package com.shanzhaozhen.classroom.admin.repository;

import com.shanzhaozhen.classroom.bean.TStudentTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TStudentTempRepository extends JpaRepository<TStudentTemp, Integer> {

    TStudentTemp findTStudentTempById(Integer id);

    TStudentTemp findTStudentTempByClassroomIdAndStudentId(Integer classroomId, Integer studentId);

    @Query("select new TStudentTemp(st, u.sysUserInfo.number, u.sysUserInfo.fullName, u.sysUserInfo.nickName, c.name) " +
            "from TStudentTemp st " +
            "left join TClassroom c on c.id = st.classroomId and c.createrId = ?1 " +
            "left join SysUser u on u.id = st.studentId")
    Page<TStudentTemp> findPageTStudentTemps(Integer userId, Pageable pageable);

    int deleteTStudentTempById(Integer id);
}

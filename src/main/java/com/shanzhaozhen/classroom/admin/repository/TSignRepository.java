package com.shanzhaozhen.classroom.admin.repository;

import com.shanzhaozhen.classroom.bean.TSign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TSignRepository extends JpaRepository<TSign, Integer> {

    @Query("select new TSign(si, u.sysUserInfo.fullName, u.sysUserInfo.number, u.sysUserInfo.nickName) " +
            "from TStudent s " +
            "left join SysUser u on u.id = s.studentId " +
            "left join TSignTask sit on sit.classId = s.classId " +
            "left join TSign si on si.signTaskId = sit.id and si.createrId = s.studentId " +
            "where sit.id = ?1 and (u.sysUserInfo.fullName like ?2 or u.sysUserInfo.nickName like ?3)")
    Page<TSign> findTSignsBySignTaskIdAndKeyword(Integer signTaskId, String keyword1, String keyword2, Pageable pageable);

    @Query("select new TSign(si, u.sysUserInfo.fullName, u.sysUserInfo.number, u.sysUserInfo.nickName) " +
            "from TStudent s " +
            "left join SysUser u on u.id = s.studentId " +
            "left join TSignTask sit on sit.classId = s.classId " +
            "left join TSign si on si.signTaskId = sit.id and si.createrId = s.studentId " +
            "where sit.id = ?1")
    List<TSign> findTSignsBySignTaskId(Integer signTaskId);

    TSign findTSignByCreaterIdAndSignTaskId(Integer createrId, Integer signTaskId);

}

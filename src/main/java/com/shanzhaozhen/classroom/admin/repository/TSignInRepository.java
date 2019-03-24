package com.shanzhaozhen.classroom.admin.repository;

import com.shanzhaozhen.classroom.bean.TSignIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TSignInRepository extends JpaRepository<TSignIn, Integer> {

    @Query("select new TSignIn(si, u.sysUserInfo.fullName as fullName, u.sysUserInfo.nickName as nickName) " +
            "from TStudent s " +
            "left join SysUser u on u.id = s.studentId " +
            "left join TSignInTask sit on sit.classId = s.classId " +
            "left join TSignIn si on si.signInTaskId = sit.id and si.createrId = s.studentId " +
            "where sit.id = ?1 and (u.sysUserInfo.fullName like ?2 or u.sysUserInfo.nickName like ?3)")
    Page<TSignIn> findTSignInsBySignInTaskIdAndKeyword(Integer signInTaskId, String keyword1, String keyword2, Pageable pageable);

    TSignIn findTSignInByCreaterIdAndSignInTaskId(Integer createrId, Integer signInTaskId);

}

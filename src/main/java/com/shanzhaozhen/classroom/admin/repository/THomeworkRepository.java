package com.shanzhaozhen.classroom.admin.repository;

import com.shanzhaozhen.classroom.bean.THomework;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface THomeworkRepository extends JpaRepository<THomework, Integer> {

    THomework findTHomeworkById(Integer id);

    @Query("select new THomework(h, u.sysUserInfo.fullName, u.sysUserInfo.nickname) " +
            "from TStudent s " +
            "left join SysUser u on u.id = s.studentId " +
            "left join THomeworkTask ht on ht.classId = s.classId " +
            "left join THomework h on h.homewordTaskId = ht.id and h.createrId = s.studentId " +
            "where ht.id = ?1 and (u.sysUserInfo.fullName like ?2 or u.sysUserInfo.nickname like ?3)")
    Page<THomework> findTHomeworksByHomeworkTaskIdAndKeyword(Integer homeworkTaskId, String keyword1, String keyword2, Pageable pageable);

}

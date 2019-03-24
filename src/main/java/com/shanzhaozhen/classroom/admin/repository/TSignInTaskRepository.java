package com.shanzhaozhen.classroom.admin.repository;

import com.shanzhaozhen.classroom.bean.TSignInTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TSignInTaskRepository extends JpaRepository<TSignInTask, Integer> {

    TSignInTask findTSignInTaskById(Integer id);

    @Query("select s from TSignInTask s where s.createrId = ?1 and (s.name like ?2 or s.outline like ?3)")
    Page<TSignInTask> findTSignInTasksByCreaterIdAndKeyword(Integer createrId, String keyword1, String keyword2, Pageable pageable);

    @Query("select s from TSignInTask s where s.createrId = ?1 and (s.name like ?2 or s.outline like ?3)")
    Page<TSignInTask> findTSignInTasksByClassIdAndKeyword(Integer classId, String keyword1, String keyword2, Pageable pageable);

    @Query("select s from TSignInTask s where s.createrId = ?1 and s.classId = ?2 and (s.name like ?3 or s.outline like ?4)")
    Page<TSignInTask> findTSignInTasksByCreaterIdAndClassIdAndKeyword(Integer createrId, Integer classId, String keyword1, String keyword2, Pageable pageable);

    List<TSignInTask> findTSignInTasksByClassIdAndAnnounceIsTrue(Integer classId);
}

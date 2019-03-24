package com.shanzhaozhen.classroom.admin.repository;

import com.shanzhaozhen.classroom.bean.THomeworkTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface THomeworkTaskRepository extends JpaRepository<THomeworkTask, Integer> {

    THomeworkTask findTHomeworkTaskById(Integer id);

    @Query("select h from THomeworkTask h where h.createrId = ?1 and (h.name like ?2 or h.outline like ?3)")
    Page<THomeworkTask> findTHomeworkTasksByCreaterIdAndKeyword(Integer createrId, String keyword1, String keyword2, Pageable pageable);

    @Query("select h from THomeworkTask h where h.createrId = ?1 and (h.name like ?2 or h.outline like ?3)")
    Page<THomeworkTask> findTHomeworkTasksByClassIdAndKeyword(Integer classId, String keyword1, String keyword2, Pageable pageable);

    @Query("select h from THomeworkTask h where h.createrId = ?1 and h.classId = ?2 and (h.name like ?3 or h.outline like ?4)")
    Page<THomeworkTask> findTHomeworkTasksByCreaterIdAndClassIdAndKeyword(Integer createrId, Integer classId, String keyword1, String keyword2, Pageable pageable);

    List<THomeworkTask> findTHomeworkTasksByClassIdAndAnnounceIsTrue(Integer classId);
}

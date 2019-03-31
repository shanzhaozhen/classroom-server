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
    Page<THomeworkTask> findTHomeworkTasksByClassroomIdAndKeyword(Integer classroomId, String keyword1, String keyword2, Pageable pageable);

    @Query("select h from THomeworkTask h where h.createrId = ?1 and h.classroomId = ?2 and (h.name like ?3 or h.outline like ?4)")
    Page<THomeworkTask> findTHomeworkTasksByCreaterIdAndClassroomIdAndKeyword(Integer createrId, Integer classroomId, String keyword1, String keyword2, Pageable pageable);

    List<THomeworkTask> findTHomeworkTasksByClassroomIdAndAnnounceIsTrue(Integer classroomId);

    @Query("select count (s.id) " +
            "from THomeworkTask ht " +
            "left join TClassroom c on c.id = ht.classroomId " +
            "left join TStudent s on s.classroomId = c.id " +
            "where ht.id = ?1")
    int countStudentNumberByHomeworkTaskId(Integer homeworkTaskId);

    @Query("select count (s.id) " +
            "from THomeworkTask ht " +
            "left join TClassroom c on c.id = ht.classroomId " +
            "left join TStudent s on s.classroomId = c.id " +
            "left join THomework h on h.createrId = s.studentId and h.homeworkTaskId = ht.id " +
            "where ht.id = ?1 and h.id is not null")
    int countSubmitNumberByHomeworkTaskId(Integer homeworkTaskId);

    int countTHomeworkTasksByClassroomId(Integer classroomId);
}

package com.shanzhaozhen.classroom.admin.repository;

import com.shanzhaozhen.classroom.bean.TSignTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TSignTaskRepository extends JpaRepository<TSignTask, Integer> {

    TSignTask findTSignTaskById(Integer id);

    @Query("select s from TSignTask s where s.createrId = ?1 and (s.name like ?2 or s.outline like ?3)")
    Page<TSignTask> findTSignTasksByCreaterIdAndKeyword(Integer createrId, String keyword1, String keyword2, Pageable pageable);

    @Query("select s from TSignTask s where s.createrId = ?1 and (s.name like ?2 or s.outline like ?3)")
    Page<TSignTask> findTSignTasksByClassroomIdAndKeyword(Integer classroomId, String keyword1, String keyword2, Pageable pageable);

    @Query("select s from TSignTask s where s.createrId = ?1 and s.classroomId = ?2 and (s.name like ?3 or s.outline like ?4)")
    Page<TSignTask> findTSignTasksByCreaterIdAndClassroomIdAndKeyword(Integer createrId, Integer classroomId, String keyword1, String keyword2, Pageable pageable);

    List<TSignTask> findTSignTasksByClassroomIdAndAnnounceIsTrue(Integer classroomId);

    @Query("select count (st.id) " +
            "from TSignTask s " +
            "left join TClassroom c on c.id = s.classroomId " +
            "left join TStudent st on st.classroomId = c.id " +
            "where s.id = ?1")
    int countStudentNumberBySignTaskId(Integer id);

    @Query("select count (st.id) " +
            "from TSignTask s " +
            "left join TClassroom c on c.id = s.classroomId " +
            "left join TStudent st on st.classroomId = c.id " +
            "left join TSign si on si.createrId = st.studentId and si.signTaskId = s.id " +
            "where s.id = ?1 and si.id is not null")
    int countAttendanceNumberBySignTaskId(Integer id);

    int countTSignTasksByClassroomId(Integer classroomId);
}

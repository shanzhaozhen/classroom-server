package com.shanzhaozhen.classroom.admin.repository;

import com.shanzhaozhen.classroom.bean.TClassroom;
import com.shanzhaozhen.classroom.param.KeyValueParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TClassroomRepository extends JpaRepository<TClassroom, Integer> {

    TClassroom findTClassroomById(Integer id);

    @Query("select new TClassroom(c, count (s.studentId) as studentNumber) " +
            "from TClassroom c " +
            "left join TStudent s on c.id = s.classroomId " +
            "where c.name like ?1 or c.outline like ?2 group by c.id")
    Page<TClassroom> findPageTClassroomsByKeyword(String keyword1, String keyword2, Pageable pageable);

    @Query("select new TClassroom(c, count (s.studentId) as studentNumber) " +
            "from TClassroom c " +
            "left join TStudent s on c.id = s.classroomId " +
            "where c.createrId = ?1 and (c.name like ?2 or c.outline like ?3) group by c.id")
    Page<TClassroom> findPageTClassroomsByCreaterIdAndKeyword(Integer createrId, String keyword1, String keyword2, Pageable pageable);

    List<TClassroom> findTClassroomsByCreaterId(Integer createrId);

    @Query("select new com.shanzhaozhen.classroom.param.KeyValueParam(c.name, c.id) from TClassroom c where c.createrId = ?1")
    List<KeyValueParam> findSimpleTClassroomsByCreaterId(Integer createrId);


    @Query("select new TClassroom(c, s.sysUserInfo) " +
            "from TClassroom c " +
            "left join SysUser s on c.createrId = s.id " +
            "where c.announce = 1 and (c.name like ?1 or c.outline like ?2)")
    List<TClassroom> findTClassroomsByKeyword(String keyword1, String keyword2);

    @Query("select new TClassroom(c, s.sysUserInfo, st.id) " +
            "from TClassroom c " +
            "left join TStudent st on st.classroomId = c.id and st.studentId = ?1 " +
            "left join SysUser s on c.createrId = s.id " +
            "where c.announce = 1 and (c.name like ?2 or c.outline like ?3)")
    List<TClassroom> findTClassroomsAndJoinStateByKeyword(Integer studentId, String keyword1, String keyword2);


    @Query("select c " +
            "from TClassroom c " +
            "left join TStudent s on c.id = s.classroomId " +
            "where s.studentId = ?1 group by c.id")
    List<TClassroom> findTClassroomsByStudentId(Integer studentId);

    @Query("select c " +
            "from TClassroom c " +
            "left join TStudent s on c.id = s.classroomId " +
            "where c.createrId = ?1 or s.studentId = ?2 group by c.id")
    List<TClassroom> findTClassroomsByCreaterIdAndStudentId(Integer createrId, Integer studentId);

    @Query("select new TClassroom(c, s.sysUserInfo) " +
            "from TClassroom c " +
            "left join SysUser s on c.createrId = s.id " +
            "where c.id = ?1")
    TClassroom findTClassroomInfoById(Integer id);
}

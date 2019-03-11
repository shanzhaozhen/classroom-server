package com.shanzhaozhen.classroom.admin.repository;

import com.shanzhaozhen.classroom.bean.TClassRoom;
import com.shanzhaozhen.classroom.param.KeyValueParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TClassRoomRepository extends JpaRepository<TClassRoom, Integer> {

    TClassRoom findTClassRoomById(Integer id);

    @Query("select new TClassRoom(c, count (s.studentId) as studentNumber) " +
            "from TClassRoom c " +
            "left join TStudent s on c.id = s.classId " +
//            "left join SysUser u on c.headmasterId = u.id " +
            "where c.headmasterId = ?1 and (c.name like ?2 or c.outline like ?3) group by c.id")
    Page<TClassRoom> findPageTClassRoomsByUserIdAndKeyword(Integer id, String keyword1, String keyword2, Pageable pageable);

    List<TClassRoom> findTClassRoomsByHeadmasterId(Integer headmasterId);

    @Query("select new com.shanzhaozhen.classroom.param.KeyValueParam(c.name, c.id) from TClassRoom c where c.headmasterId = ?1")
    List<KeyValueParam> findSimpleTClassRoomsByHeadmasterId(Integer headmasterId);


}

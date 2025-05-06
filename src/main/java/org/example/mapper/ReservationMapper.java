package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.entity.pojo.Reservation;

import java.time.LocalDateTime;

/**
 * @author zklee
 * date 2025/4/28
 */
@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {

    @Select("""
        SELECT COUNT(1)
        FROM reservation
        WHERE meeting_room_id = #{roomId}
            AND status IN ('lock', 'reserved')
            AND start_time < #{endTime}
            AND end_time > #{startTime}
    """)
    Integer countConflictReservations(
            @Param("roomId") Long roomId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}

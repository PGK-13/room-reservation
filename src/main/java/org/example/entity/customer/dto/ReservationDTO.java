package org.example.entity.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zklee
 * date 2025/4/28
 */
@Data
public class ReservationDTO {
    @JsonProperty("meeting_room_id")
    private Long meetingRoomId;

    @JsonProperty("start_time")
    private LocalDateTime startTime; // 开始时间

    @JsonProperty("end_time")
    private LocalDateTime endTime; // 结束时间
}

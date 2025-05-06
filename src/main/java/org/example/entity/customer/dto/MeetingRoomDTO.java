package org.example.entity.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zklee
 * date 2025/4/28
 */
@Data
public class MeetingRoomDTO {
    private String type; // 类型（教室型/圆桌型）

    @JsonProperty("seat_count")
    private Integer seatCount; // 座位数

    @JsonProperty("has_projector")
    private Integer hasProjector; // 是否有投影仪

    @JsonProperty("has_sound")
    private Integer hasSound; // 是否有音响系统

    @JsonProperty("has_network")
    private Integer hasNetwork; // 是否有网络支持

    @JsonProperty("start_time")
    private LocalDateTime startTime; // 会议开始时间

    @JsonProperty("end_time")
    private LocalDateTime endTime; // 会议结束时间

    private Integer pageNo;

    private Integer pageSize;
}

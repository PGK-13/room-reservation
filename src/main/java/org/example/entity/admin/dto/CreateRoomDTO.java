package org.example.entity.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zklee
 * date 2025/4/29
 */
@Data
public class CreateRoomDTO {

    private String name; // 会议室名称

    private String type; // 类型（教室型/圆桌型）

    private Integer area; // 面积（平方米）

    @JsonProperty("seat_count")
    private Integer seatCount; // 座位数

    @JsonProperty("has_projector")
    private Integer hasProjector; // 是否有投影仪

    @JsonProperty("has_sound")
    private Integer hasSound; // 是否有音响系统

    @JsonProperty("has_network")
    private Integer hasNetwork; // 是否有网络支持

    @JsonProperty("price_per_hour")
    private Double pricePerHour; // 每小时租赁价格

    @JsonProperty("is_under_maintenance")
    private Integer isUnderMaintenance; // 是否处于维修状态
}

package org.example.entity.customer.vo;

import lombok.Data;

/**
 * @author zklee
 * date 2025/4/28
 */
@Data
public class MeetingRoomVO {
    private Long id;

    private String name; // 会议室名称

    private String type; // 类型（教室型/圆桌型）

    private Integer area; // 面积（平方米）

    private Integer seatCount; // 座位数

    private Integer hasProjector; // 是否有投影仪

    private Integer hasSound; // 是否有音响系统

    private Integer hasNetwork; // 是否有网络支持

    private Double pricePerHour; // 每小时租赁价格

    private Integer isUnderMaintenance; // 是否处于维修状态

}

package org.example.entity.customer.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zklee
 * date 2025/4/28
 */
@Data
public class ReservationVO {
    private Long id;

    private Long meetingRoomId; // 会议室ID

    private LocalDateTime startTime; // 开始时间

    private LocalDateTime endTime; // 结束时间

    private String status; // 状态（lock锁定 / reserved预定 / cancelled取消）

    private String payStatus = "未支付"; // 支付状态（未支付/已支付）

    private Double totalPrice; // 总金额
}

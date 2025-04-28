package org.example.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zklee
 * date  2025/4/28
 */
@Data
@TableName("reservation")
public class Reservation {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId; // 客户ID

    private Long meetingRoomId; // 会议室ID

    private LocalDateTime startTime; // 开始时间

    private LocalDateTime endTime; // 结束时间

    private String status; // 状态（lock锁定 / reserved预定 / cancelled取消）

    private String payStatus = "未支付"; // 支付状态（未支付/已支付）

    private Double totalPrice; // 总金额

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

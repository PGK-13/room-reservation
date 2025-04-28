package org.example.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zklee
 * github https://github.com/PGK-13
 * date 2025/4/28
 */
@Data
@TableName("cancel_application")
public class CancelApplication {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long reservationId; // 对应预约ID

    private Long userId; // 申请人（客户）ID

    private String reason; // 取消原因

    private Double refundAmount = 0.0; // 应退金额

    private String reviewStatus = "待审核"; // 审核状态（待审核/通过/拒绝）

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

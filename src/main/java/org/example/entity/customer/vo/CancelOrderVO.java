package org.example.entity.customer.vo;

import lombok.Data;

/**
 * @author zklee
 * date 2025/4/29
 */
@Data
public class CancelOrderVO {
    private Long id;

    private Long reservationId; // 对应预约ID

    private Long userId; // 申请人（客户）ID

    private String reason; // 取消原因

    private Double refundAmount = 0.0; // 应退金额

    private String reviewStatus = "待审核"; // 审核状态（待审核/通过/拒绝）
}

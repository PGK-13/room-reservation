package org.example.entity.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zklee
 * date 2025/4/29
 */
@Data
public class CancelReasonDTO {
    @JsonProperty("reservation_id")
    private Long reservationId;

    private String reason;
}

package org.example.entity.employee.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zklee
 * date 2025/4/29
 */
@Data
public class MaintenanceDTO {
    // 会议室id
    private Long id;

    @JsonProperty("is_under_maintenance")
    private Integer isUnderMaintenance;
}

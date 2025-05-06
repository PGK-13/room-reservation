package org.example.entity.employee.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zklee
 * date 2025/4/29
 */
@Data
public class ResultDTO {
    private Long id;

    @JsonProperty("review_status")
    private String reviewStatus;
}

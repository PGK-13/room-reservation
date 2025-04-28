package org.example.entity.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/28
 */
@Data
public class CustomerDTO {
    private Long id;

    private String username; // 登录账号

    private String name; // 姓名

    private String role; // 角色（admin/employee/customer）

    private String company; // 所属公司（仅客户有）

    private String phone; // 联系电话（仅客户有）

    private Integer status = 1; // 账号状态（0正常，1待审核，2冻结）

    @JsonProperty("audit_reason")
    private String auditReason; // 审核或冻结的备注原因

    private double balance;
}

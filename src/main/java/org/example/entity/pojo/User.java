package org.example.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;

/**
 * 用户实体类
 */
@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username; // 登录账号

    private String password; // 登录密码（加密存储）

    private String name; // 姓名

    private String role; // 角色（admin/employee/customer）

    private String company; // 所属公司（仅客户有）

    private String phone; // 联系电话（仅客户有）

    private Integer status = 1; // 账号状态（0正常，1待审核，2冻结）

    private String auditReason; // 审核或冻结的备注原因

    private double balance;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
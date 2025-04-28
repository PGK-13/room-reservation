package org.example.entity.user.dto;

import lombok.Data;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/27
 */
@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String name;
    private String role;
    private String company;
    private String phone;
}

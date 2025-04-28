package org.example.entity.user.vo;

import lombok.Data;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/27
 */
@Data
public class UserLoginVO {
    /**
     * token
     */
    private String token;
    /**
     * 用户id
     */
    private Long userId;
}

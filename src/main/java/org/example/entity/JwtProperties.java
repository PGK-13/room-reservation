package org.example.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/27
 */
@Component
@ConfigurationProperties(prefix = "mr.jwt")
public class JwtProperties {
    /**
     * 登录 jwt令牌管理
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;
}

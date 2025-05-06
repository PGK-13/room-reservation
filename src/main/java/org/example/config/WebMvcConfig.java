package org.example.config;

import org.example.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zklee
 * date 2025/4/28
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(// 放行接口
                        "/api/auth/login",
                        "/api/auth/register",
                        "/logout",
                        "/error",
                        // 放行静态资源（需带斜杠和资源路径前缀）
                        "/index.html",
                        "/css/**",
                        "/js/**",
                        "/images/**");
    }
}

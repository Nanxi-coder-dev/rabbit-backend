package com.rabbit.config;

import com.rabbit.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置：CORS + 拦截器注册。
 * 放行名单只放公共接口（登录、首页、分类、商品），
 * 购物车等需要登录的路径不要放行，否则拿不到 token。
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    /** 开发阶段允许所有来源，方便前端 5173 端口联调 */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                // 公共接口不需要 token
                .excludePathPatterns(
                        "/login",
                        "/home/**",
                        "/category",
                        "/category/sub/filter",
                        "/category/goods/temporary",
                        "/goods",
                        "/goods/hot",
                        "/goods/relevant",
                        "/error"
                );
    }
}
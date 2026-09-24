package com.rabbit.interceptor;

import com.rabbit.common.BizException;
import com.rabbit.common.ResultCode;
import com.rabbit.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT 拦截器。
 * 校验请求头 Authorization: Bearer <token>。
 * 校验通过后把 userId 挂到 request 上，Controller 可直接取用。
 * 放行名单在 WebConfig 里配置。
 */
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        // 浏览器预检请求直接放行，否则跨域会失败
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) return true;

        String auth = request.getHeader("Authorization");
        if (!StringUtils.hasText(auth) || !auth.startsWith("Bearer ")) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }

        String token = auth.substring(7);
        try {
            Claims claims = jwtUtil.parseToken(token);
            // 后续 Controller / Service 可通过 request.getAttribute("userId") 获取
            request.setAttribute("userId", claims.get("userId"));
            return true;
        } catch (Exception e) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }
    }
}
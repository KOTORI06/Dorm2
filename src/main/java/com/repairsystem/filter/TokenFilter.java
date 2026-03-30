package com.repairsystem.filter;

import com.repairsystem.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * JWT 令牌过滤器
 * 拦截请求并验证 JWT 令牌的有效性
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 过滤器的核心处理方法，每次请求都会被调用
     * @param request HTTP 请求对象
     * @param response HTTP 响应对象
     * @param filterChain 过滤器链，用于将请求传递给下一个过滤器或目标资源
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 获取请求的 URI 路径
        String uri = request.getRequestURI();

        // 放行登录和注册接口，不需要令牌验证
        if (uri.contains("/api/login") || uri.contains("/api/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 从请求头中获取 token
        String token = request.getHeader("token");

        // 如果请求头中没有 token，尝试从请求参数中获取
        if (!StringUtils.hasText(token)) {
            token = request.getParameter("token");
        }

        // 如果 token 为空，返回错误信息
        if (!StringUtils.hasText(token)) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write("{\"code\":1,\"msg\":\"令牌不能为空\"}");
            writer.flush();
            return;
        }

        // 验证令牌
        try {
            // 使用 JwtUtil 验证令牌是否有效
            boolean isValid = jwtUtil.validateToken(token);
            if (!isValid) {
                // 令牌无效或已过期，返回错误信息
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write("{\"code\":1,\"msg\":\"令牌无效或已过期\"}");
                writer.flush();
                return;
            }

            // 从令牌中提取用户 ID 和角色信息
            String userId = jwtUtil.getUserIdFromToken(token);
            String role = jwtUtil.getRoleFromToken(token);
            // 将用户信息存入请求属性，供后续 Controller 使用
            request.setAttribute("userId", userId);
            request.setAttribute("role", role);

        } catch (Exception e) {
            // 捕获令牌验证过程中的异常，返回错误信息
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write("{\"code\":1,\"msg\":\"令牌验证失败：" + e.getMessage() + "\"}");
            writer.flush();
            return;
        }

        // 令牌验证通过，将请求传递给下一个过滤器或目标资源
        filterChain.doFilter(request, response);
    }
}
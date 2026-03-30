package com.repairsystem.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {


    private static final String SECRET_KEY = "ThisIsASecretKeyForJWTSigningWith256BitsLength!@#$%";
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    /**
     * 生成 JWT 令牌
     * @param userId 用户 ID
     * @param role 用户角色
     * @return 加密后的 JWT 令牌字符串
     */
    public String generateToken(String userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }



    /**
     * 解析 JWT 令牌，获取声明信息
     * @param token JWT 令牌
     * @return Claims 对象，包含令牌中的所有声明
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从 JWT 令牌中获取用户 ID
     * @param token JWT 令牌
     * @return 用户 ID
     */
    public String getUserIdFromToken(String token) {
        return parseToken(token).getSubject();
    }


    /**
     * 从 JWT 令牌中获取用户角色
     * @param token JWT 令牌
     * @return 用户角色
     */
    public String getRoleFromToken(String token) {
        return parseToken(token).get("role", String.class);
    }

    /**
     * 判断 JWT 令牌是否已过期
     * @param token JWT 令牌
     * @return true 表示已过期，false 表示未过期
     */
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = parseToken(token).getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 验证 JWT 令牌是否有效
     * @param token JWT 令牌
     * @return true 表示令牌有效，false 表示令牌无效
     */
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}
package com.shanzhaozhen.classroom.config;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MyJwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(MyJwtTokenProvider.class);


    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.remember_expiration}")
    private long remember_expiration;

    /**
     * iss：发行人
     * exp：到期时间
     * sub：主题
     * aud：用户
     * nbf：在此之前不可用
     * iat：发布时间
     * jti：JWT ID用于标识该JWT
     */
    // 创建token
    public String createToken(String username, List<String> roles, boolean isRemember) {

        Map<String, Object> map = new HashMap<>();

        map.put("roles", roles);

        /**
         * 按照jwt的规定，最后请求的时候应该是 `Bearer token`
         */
        return tokenHead + " " + Jwts.builder()
                /**
                 * 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值
                 * 一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                 */
                .setClaims(map)
                .setSubject(username)
                .setIssuer(issuer)          //iss
                .setIssuedAt(new Date())            //iat: jwt的签发时间
                .setExpiration(new Date(System.currentTimeMillis() + (isRemember ? remember_expiration : expiration) * 1000))        //设置过期时间
                .signWith(SignatureAlgorithm.HS512, secret)         //设置签名使用的签名算法和签名使用的秘钥
                .compact();
    }

    /**
     * 校验token的签名
     * @param authToken
     * @return
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    // 从token中获取用户名
    public String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    // 获取用户角色
    public List<String> getUserRoles(String token){
        return (List<String>) getTokenBody(token).get("roles");
    }

    // 是否已过期
    public boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }

    private Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }



}

package com.shanzhaozhen.classroom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyJwtAuthenticationFilter extends OncePerRequestFilter{

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private MyJwtTokenProvider myJwtTokenProvider;

    // 1.从每个请求header获取token
    // 2.调用前面写的validateToken方法对token进行合法性验证
    // 3.解析得到username，并从database取出用户相关信息权限
    // 4.把用户信息(role等)放进SecurityContext以备整个请求过程使用。
    //  （例如哪里需要判断用户权限是否足够时可以直接从SecurityContext取出去check）
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = getJwtTokenFromRequest(httpServletRequest);

        // 如果请求头中有token而且token校验正确，则进行解析，并且设置认证信息
        if (StringUtils.hasText(jwtToken)) {

            /**
             * token 过期时重新登录
             */
            if (!myJwtTokenProvider.validateToken(jwtToken)) {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "登录失效，请重新登陆");
                return;
            }

            SecurityContextHolder.getContext().setAuthentication(this.createAuthentication(jwtToken));
        }

        /**
         * 不管成不成功都交给下一个过滤器判断
         */
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    /**
     * 从httpServletRequest获取token
     * @param httpServletRequest
     * @return
     */
    private String getJwtTokenFromRequest(HttpServletRequest httpServletRequest) {
        String jwtToken = httpServletRequest.getHeader(header);
        if (StringUtils.hasText(jwtToken) && jwtToken.startsWith(tokenHead + " ")) {
            return jwtToken.substring((tokenHead + " ").length());
//            return jwtToken.substring((tokenHead + " ").length());
        }
        return null;
    }

    // 这里从token中获取用户信息并新建一个UsernamePasswordAuthenticationToken供给过滤链进行权限过滤
    private UsernamePasswordAuthenticationToken createAuthentication(String tokenHeader) {

        String token = tokenHeader.replace(tokenHead + " ", "");

        String username = myJwtTokenProvider.getUsername(token);
        List<String> roles = myJwtTokenProvider.getUserRoles(token);

        if (StringUtils.isEmpty(username)) {
            return null;
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }




}

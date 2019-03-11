package com.shanzhaozhen.classroom.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.shanzhaozhen.classroom.bean.SysUser;
import com.shanzhaozhen.classroom.common.JwtErrorConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.*;

@Component
public class MyUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Value("${jwt.header}")
    private String header;

    private ThreadLocal rememberMe = new ThreadLocal();

    @Autowired
    private MyJwtTokenProvider myJwtTokenProvider;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    protected MyUsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        //从json中获取username和password
        String body = StreamUtils.copyToString(httpServletRequest.getInputStream(), Charset.forName("UTF-8"));

        String username = null;
        String password = null;

        try {
            if(StringUtils.hasText(body)) {
                JSONObject jsonObj = JSON.parseObject(body);
                username = jsonObj.getString("username");
                password = jsonObj.getString("password");
                rememberMe.set(jsonObj.getBooleanValue("rememberMe"));
            }
        } catch (JSONException e) {
            this.unsuccessfulAuthentication(httpServletRequest, httpServletResponse, null);
            return null;
        }

        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        username = username.trim();

        /**
         * UsernamePasswordAuthenticationToken 是 Authentication 的实现类
         *
         * 封装到token中提交
         */
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);


        /**
         * authenticate()接受一个token参数,返回一个完全经过身份验证的对象，包括证书.
         * 里并没有对用户名密码进行验证,而是使用 AuthenticationProvider 提供的 authenticate 方法返回一个完全经过身份验证的对象，包括证书.
         * Authentication authenticate = authenticationManager.authenticate(authenticationToken);
         */
        return this.getAuthenticationManager().authenticate(authRequest);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        boolean isRemember = (boolean) rememberMe.get();

        String username = ((SysUser) authResult.getPrincipal()).getUsername();

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authResult.getAuthorities();
        List<String> roles = new ArrayList<>();
        for(GrantedAuthority g : authorities){
            roles.add(g.getAuthority());
        }

        String token = myJwtTokenProvider.createToken(username, roles, isRemember);

//         返回创建成功的token
//        response.setHeader(header, token);

        // 登陆成功返回
        this.sendResult(response, true, token, "登陆成功");

//        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        super.unsuccessfulAuthentication(request, response, failed);

//        String msg = "authentication failed, reason: " + (failed == null ? "param error" : failed.getMessage());
        String msg = failed == null ? "param error" : failed.getMessage();

//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, msg);
        this.sendResult(response, false, null, msg);

    }

    private void sendResult(HttpServletResponse response, boolean success, String token, String msg) throws IOException {
        Map<String, Object> map = new HashMap<>();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        map.put("timestamp", new Date(System.currentTimeMillis()));

        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
            map.put("success", true);
            map.put("code", JwtErrorConst.LOGIN_SUCCESS.getCode());
            map.put("message", msg);
            map.put("access-token", token);
            writer.write(JSONObject.toJSONString(map));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            map.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            map.put("code", JwtErrorConst.LOGIN_ERROR.getCode());
            map.put("message", msg);
            writer.write(JSONObject.toJSONString(map));
        }

    }

}

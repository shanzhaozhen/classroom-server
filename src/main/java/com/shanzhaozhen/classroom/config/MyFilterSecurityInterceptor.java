package com.shanzhaozhen.classroom.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;

    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    /**
     * filterInvocation里面有一个被拦截的url
     * 里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取filterInvocation对应的所有权限
     * 再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
     */
    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        InterceptorStatusToken interceptorStatusToken = super.beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(interceptorStatusToken, null);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 所有的请求到了这一个filter，如果这个filter之前没有执行过的话，
     * 那么首先执行的InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
     * 这个是由AbstractSecurityInterceptor提供。它就是spring security处理鉴权的入口。
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //这个类的作用本身很简单，就是把doFilter传进来的ServletRequest,ServletResponse和FilterChain对象保存起来，
        //供FilterSecurityInterceptor的处理代码调用
        FilterInvocation filterInvocation = new FilterInvocation(servletRequest, servletResponse, filterChain);
        this.invoke(filterInvocation);
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.myFilterInvocationSecurityMetadataSource;
    }
}

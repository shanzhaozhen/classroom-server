package com.shanzhaozhen.classroom.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyDruidConfig {

    @Value("${druid.username}")
    private String loginUsername;

    @Value("${druid.password}")
    private String loginPassword;

    @Value("${druid.allow}")
    private String allow;

    @Value("${druid.deny}")
    private String deny;


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    /**
     * 配置Druid的监控
     * 配置一个管理后台的servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", loginUsername);
        initParams.put("loginPassword", loginPassword);
        initParams.put("allow", allow);                 //默认所有ip
        initParams.put("deny", deny);                   //拒绝访问ip
        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     * 配置一个web监控的filter
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        bean.addUrlPatterns("/*");
        bean.addInitParameter("exclusions", "*.js,*.css,*.gif,*.jpg,*.png,*.ico,/druid/*");
        return bean;
    }



}

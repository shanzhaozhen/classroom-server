package com.shanzhaozhen.classroom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Value("${upload.relativePath}")
    private String relativePath;

    @Value("${upload.realPath}")
    private String realPath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("public/login");
        registry.addViewController("/test1").setViewName("admin/starter");
        registry.addViewController("/test2").setViewName("admin/starter2");
//        registry.addViewController("/register").setViewName("public/register");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);                                  //过滤时优先执行
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(relativePath).addResourceLocations("file:" + realPath);
    }
}

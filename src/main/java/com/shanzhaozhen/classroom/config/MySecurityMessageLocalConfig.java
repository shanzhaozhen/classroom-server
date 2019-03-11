package com.shanzhaozhen.classroom.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

/**
 * 该类主要用来将SpringSecurity的错误信息提示本地化
 */
@Configuration
public class MySecurityMessageLocalConfig {

    @Bean
    public MessageSource messageSource() {
        Locale.setDefault(Locale.CHINA);
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("classpath:org/springframework/security/messages_zh_CN");
//        messageSource.addBasenames("classpath:security/messages_zh_CN");

        return messageSource;
    }

}

package com.nagi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

/**
 * 管理DispatcherServlet引用的WebApplicationContext（包含HandlerMapping、HandlerAdapter等指定类型的bean）代理
 * 使用@EnableWebMvc（导入DelegatingWebMvcConfiguration组件，Spring Boot在引入Starter时可自动装配，
 * 可以省略此注解）开启对Spring MVC的支持
 * 实现WebMvcConfigurer接口对导入的MVC容器进行管理，可以添加自定义规则（增删组件、修改处理逻辑等）
 * @EnableWebMvc 只能添加在一个配置类上，但是可以有多个配置类实现WebMvcConfigurer
 * 要是需要自定义WebMvcConfigurer暴露接口以外的规则，可以直接继承WebMvcConfigurationSupport或者DelegatingWebMvcConfiguration
 * @EnableWebMvc 和WebMvcConfigurationSupport和DelegatingWebMvcConfiguration只能存在一个（避免MVC容器重复注册）
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = MyConfiguration.class) // 指定配置类扫描的组件范围
public class MyConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        // 开启对uri的Matrix Variables的处理功能（保留分号标识）
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }
}

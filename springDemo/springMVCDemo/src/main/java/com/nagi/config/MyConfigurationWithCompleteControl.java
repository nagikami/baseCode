package com.nagi.config;

import com.nagi.mapping.MyHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.validation.Validator;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * 继承DelegatingWebMvcConfiguration，实现对MVC容器（WebApplicationContext）的完全控制
 */
//@Configuration // 已使用@EnableWebMvc注册MVC容器，避免重复注册启动失败，故注释
public class MyConfigurationWithCompleteControl extends DelegatingWebMvcConfiguration {
    @Bean
    @Override
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(ContentNegotiationManager contentNegotiationManager, FormattingConversionService conversionService, Validator validator) {
        // 可以自定义组件，或者返回默认组件（父类方法）
        return super.requestMappingHandlerAdapter(contentNegotiationManager, conversionService, validator);
    }

    // 注册自定义RequestMappingHandlerMapping
    @Bean
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping(ContentNegotiationManager contentNegotiationManager, FormattingConversionService conversionService, ResourceUrlProvider resourceUrlProvider) {
        return new MyHandlerMapping();
    }

    @Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {
        registry.enableContentNegotiation(new MappingJackson2JsonView());
        registry.beanName();
        registry.jsp("/WEB-INF/static/", ".jsp").cache(false);
    }
}

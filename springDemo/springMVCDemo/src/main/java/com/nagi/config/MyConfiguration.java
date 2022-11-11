package com.nagi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
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

    /**
     * 配置视图解析器链，ViewResolver包含视图名到视图资源的映射，可以根据视图名或者Header信息匹配对应的视图并返回，
     * DispatcherServlet再通过调用视图的render(model,request,response)进行渲染
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        /**
         * 将ContentNegotiatingViewResolver启用并放在视图解析器链的最前端，
         * 根据Accept的media类型匹配对应视图，添加MappingJackson2JsonView默认视图，
         * 该视图使用Jackson2的ObjectMapper将model的数据编码为JSON
         */
        registry.enableContentNegotiation(new MappingJackson2JsonView());
        registry.beanName();
        /**
         * 注册jsp视图解析器InternalResourceViewResolver，默认视图资源为/WEB-INF/下以.jsp结尾的文件
         * View实例基于jsp创建并使用model渲染
         * InternalResourceViewResolver需要通过RequestDispatcher转发来判断jsp文件是否存在，
         * 所以要放在解析器链的最后
         */
        registry.jsp("/WEB-INF/static/", ".jsp").cache(false);
        /**
         * 注册freeMaker视图解析器FreeMarkerViewResolver，freemarker包括视图资源等配置需要作为bean注册到spring
         */
        //registry.freeMarker().cache(false);
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("/freemarker");
        return freeMarkerConfigurer;
    }

    /**
     * ViewController用于略过controller逻辑，直接返回View，可以用于静态资源
     * 在@RequestMapping配置过的url会优先交给controller处理
     * controller会返回错误信息帮助debug，而ViewController不会，因此不建议使用ViewController
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/resource").setViewName("home");
    }

    @Bean
    public View index() {
        return new JstlView("/WEB-INF/static/index.jsp");
    }
}

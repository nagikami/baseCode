package com.nagi.config;

import com.nagi.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.util.UrlPathHelper;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

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

    private Logger logger = Logger.getLogger("myConfiguration");

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        // 根据请求参数siteLanguage的值调用webApplicationContext中的LocaleResolver的setLocale方法修改locale
        localeChangeInterceptor.setParamName("siteLanguage ");
        registry.addInterceptor(localeChangeInterceptor);
        registry.addInterceptor(new MyInterceptor());
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        // 开启对uri的Matrix Variables的处理功能（保留分号标识）
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }

    /**
     * 配置视图解析器链，ViewResolver包含视图名到视图资源的映射，可以根据视图名或者Header信息匹配对应的视图（包含uri）并返回，
     * DispatcherServlet再通过调用视图的render(model,request,response)进行渲染
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        /**
         * 将ContentNegotiatingViewResolver启用并放在视图解析器链的最前端，
         * 根据Accept的media选择符合客户端请求的视图，并将处理后的视图代理给下一个resolver继续处理
         * 添加MappingJackson2JsonView默认视图，该视图使用Jackson2的ObjectMapper将model的数据编码为JSON
         */
        registry.enableContentNegotiation(new MappingJackson2JsonView());
        registry.beanName();
        /**
         * 注册jsp视图解析器InternalResourceViewResolver，默认视图资源为/WEB-INF/下以.jsp结尾的文件
         * View实例基于jsp创建并使用model渲染
         * InternalResourceViewResolver需要通过RequestDispatcher转发来判断jsp文件是否存在，
         * 所以要放在解析器链的最后
         * RequestDispatcher将请求转发到JspServlet(由tomcat初始化，维护uri到实际web文件的映射)，返回jsp页面
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
        freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/freemarker");
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

    // 注册beanName视图，视图名为index
    @Bean
    public View index() {
        return new JstlView("/WEB-INF/static/index.jsp");
    }

    /**
     * 使用Apache Commons FileUpload实现文件上传，引入commons依赖，提供在不同servlet之间的可移植性
     * 也可以使用容器自己的解析器，独立tomcat在MyWebAppInitializer配置，内置tomcat在MyWebApplicationInitializer配置
     * 解析器配置，然后添加name为multipartResolver的StandardServletMultipartResolver类型的bean
     */
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding(StandardCharsets.UTF_8.name());
        commonsMultipartResolver.setMaxUploadSize(20 * 1024 * 1024);
        /**
         * 设置为true，则将multipart（文件上传）的处理推迟到handler调用里访问文件或请求参数时
         * 而不是在调用resolveMultipart时处理
         */
        commonsMultipartResolver.setResolveLazily(false);
        return commonsMultipartResolver;
    }


    // 开启支持异步请求后，在此回调函数进行配置，spring装填的dispatcherServlet默认支持异步请求
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // 用于执行handler返回的callable实例
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newCachedThreadPool()));
    }

    // 配置全局CORS策略
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/app1/**")
                .allowedOrigins("http://127.0.0.1:8888")
                .allowedMethods("PUT","GET");
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.forEach(resolver -> logger.info("default exception resolver: " + resolver.getClass().getSimpleName()));
    }

    /**
     * 添加资源处理配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 设置url pattern为/static/**，资源路径为web application root下的public
         * 和classpath下的/public，因为没有尾缀匹配，请求时需要加扩展名
         */
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/public", "classpath:/public/")
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(365)));
    }

    /**
     * tomcat用name为default的默认servlet处理静态资源
     * spring默认关闭内置tomcat的default servlet的注册，所有请求由配置了/映射的DispatcherServlet处理，若仍然想将
     * 静态资源转发给default servlet处理，可通过该回调方法配置
     * spring会配置DefaultServletHttpRequestHandler，并将其url mapping设置为/**，且优先级设置为最低
     * DefaultServletHttpRequestHandler可以将请求转发到default servlet
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        /**
         * 不指定defaultServletName时，默认根据Tomcat, Jetty, GlassFish, JBoss, Resin, WebLogic, and WebSphere
         * 等容器提供的默认servlet name设置defaultServletName
         */
        // 开启后，会因为容器没有注册default servlet导致dispatch失败
        //configurer.enable("default");
    }
}

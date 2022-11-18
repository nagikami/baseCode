package com.nagi.config;

import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;

/**
 * 继承AbstractAnnotationConfigDispatcherServletInitializer通过spring配置注册DispatcherServlet到servlet容器（ServletContext）
 * 或继承AbstractDispatcherServletInitializer通过xml配置注册
 * 还可以通过实现WebApplicationInitializer接口，使用servlet的xml或者spring配置文件创建DispatcherServlet并注册
 *
 * tomcat使用SPI发现实现了ServletContainerInitializer接口（服务）的spring类SpringServletContainerInitializer
 * SpringServletContainerInitializer使用注解声明WebApplicationInitializer类型为处理类型，
 * tomcat查找所有实现WebApplicationInitializer的类传入SpringServletContainerInitializer的onStartup进行处理
 *
 * 但是spring的内置tomcat关闭了ServletContainerInitializer服务发现机制，导致此功能不可用
 * 参考https://github.com/spring-projects/spring-boot/issues/321
 * 需要通过spring提供的org.springframework.boot.web.servlet.ServletContextInitializer配置ServletContext
 */
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * 不设置共享的root WebApplicationContext
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    /**
     * 指定servlet WebApplicationContext的配置类
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { MyConfiguration.class };
    }

    /**
     * 设置servlet mapping
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/app1" };
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        /**
         * 设置servlet multipart配置，再用multipartResolver名称注册StandardServletMultipartResolver类型bean，
         * 即可使用servlet的MultipartResolver
         */
        //registration.setMultipartConfig(new MultipartConfigElement("/tmp"));
        // 开启日志中请求参数和请求头等敏感数据的显示（默认masked）
        registration.setInitParameter("enableLoggingRequestDetails", "true");
    }

    // 注册filter
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] {new FormContentFilter()};
    }
}

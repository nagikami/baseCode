package com.nagi.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 继承AbstractAnnotationConfigDispatcherServletInitializer通过spring配置注册DispatcherServlet到servlet容器（ServletContext）
 * 或继承AbstractDispatcherServletInitializer通过xml配置注册
 * 还可以通过实现WebApplicationInitializer接口，使用servlet的xml或者spring配置文件创建DispatcherServlet并注册
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
}

package com.nagi.config;

import com.nagi.filter.MyFilter;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

@Component
public class MyWebApplicationInitializer implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext annotationConfigWebApplicationContext = new AnnotationConfigWebApplicationContext();
        annotationConfigWebApplicationContext.register(MyConfiguration.class);
        ServletRegistration.Dynamic myServlet = servletContext.addServlet("myServlet", new DispatcherServlet(annotationConfigWebApplicationContext));
        myServlet.setLoadOnStartup(1);
        myServlet.addMapping("/app1");
        // servlet设置支持异步请求，自定义dispatcherServlet默认为false
        myServlet.setAsyncSupported(true);
        FilterRegistration.Dynamic myFilter = servletContext.addFilter("myFilter", new MyFilter());
        myFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "/*");
        // filter设置支持异步请求
        myFilter.setAsyncSupported(true);
    }
}

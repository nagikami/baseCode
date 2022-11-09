package com.nagi.config;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Component
public class MyWebApplicationInitializer implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext annotationConfigWebApplicationContext = new AnnotationConfigWebApplicationContext();
        annotationConfigWebApplicationContext.register(MyConfiguration.class);
        ServletRegistration.Dynamic myServlet = servletContext.addServlet("myServlet", new DispatcherServlet(annotationConfigWebApplicationContext));
        myServlet.setLoadOnStartup(1);
        myServlet.addMapping("/app1");
    }
}

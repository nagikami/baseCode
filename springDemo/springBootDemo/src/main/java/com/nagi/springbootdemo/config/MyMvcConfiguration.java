package com.nagi.springbootdemo.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        // 配置路由拦截鉴权，为除了/acc/doLogin的所有path检查是否登录
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 登录检查
            StpUtil.checkLogin();
            // 自定义路由匹配检查
                    SaRouter.match("/acc/**").check(r -> System.out.println("request path /acc/**"));
                    // stop提前退出匹配链
                    SaRouter.match("/info").check(r -> System.out.println("request info")).stop();
                    // back直接写入response并返回
                    SaRouter.match("/desc").back("this is a description");
                    // 使用free声明一个独立的作用域，在free里的stop，只会跳出free，跳出后继续执行后续匹配链
                    SaRouter.match("/hello").free(r -> {
                        SaRouter.match("/hello/info").check(checkR -> System.out.println("info")).stop();
                    });
                })).addPathPatterns("/**")
                .excludePathPatterns("/acc/doLogin");
    }
}

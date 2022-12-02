package com.nagi.springbootdemo.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouter;
import com.nagi.springbootdemo.controller.HelloController;
import com.nagi.springbootdemo.model.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false) // false: Lite模式，多例；true: Full模式，单例
@ConditionalOnBean(name = "user")
@EnableConfigurationProperties // 注册通过@ConfigurationProperties绑定了配置的组件到容器
public class MyConfig {
    @Bean // 给容器中添加组件。以方法名作为组件的id。返回类型就是组件类型。返回的值，就是组件在容器中的实例
    public User myUser() {
        User user = new User(null, "tom", 1000d, "test", 18, 1);
        return user;
    }

    @Bean("nagi") // 指定id
    public User user01() {
        User user = new User(null, "nagi", 1000d, "test", 18, 1);
        return user;
    }

    // 使用BeanNameUrlHandlerMapping
    @Bean("/hello")
    public HelloController hello() {
        return new HelloController();
    }

    /**
     * 注册 [Sa-Token全局过滤器]
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                // 指定拦截路由与放行路由
                .addExclude("/hello")
                // 认证函数: 每次请求执行
                .setAuth(obj -> {
                    System.out.println("Sa-Token filter");
                    SaRouter.match("/authority").check(r -> System.out.println("filter authority"));
                })
                // 异常处理函数：每次认证函数发生异常时执行此函数
                .setError(e -> {
                    System.out.println(e.getMessage());
                    return "error from filter";
                })
                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(r -> {
                            // ---------- 设置一些安全响应头 ----------
                            SaHolder.getResponse()
                                    // 服务器名称
                                    .setServer("sa-server")
                                    // 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
                                    .setHeader("X-Frame-Options", "SAMEORIGIN")
                                    // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
                                    .setHeader("X-XSS-Protection", "1; mode=block")
                                    // 禁用浏览器内容嗅探
                                    .setHeader("X-Content-Type-Options", "nosniff");
                        }
                );
    }
}

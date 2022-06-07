package com.nagi.springbootdemo.config;


import com.nagi.springbootdemo.entity.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false) // false： Lite模式，多例；true：Full模式，单例
@ConditionalOnBean(name = "user")
@EnableConfigurationProperties // 开启属性配置
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
}

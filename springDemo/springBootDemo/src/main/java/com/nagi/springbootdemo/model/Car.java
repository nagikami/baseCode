package com.nagi.springbootdemo.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component // 将组件注册到容器，当类在第三方包时可以使用@EnableConfigurationProperties注册到容器
@ConfigurationProperties(prefix = "car") // 绑定配置和bean属性
@Data
public class Car {
    private String type;
    private String color;
}

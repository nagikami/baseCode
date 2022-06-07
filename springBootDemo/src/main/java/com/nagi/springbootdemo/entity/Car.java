package com.nagi.springbootdemo.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "car") // 根据配置文件自动配置bean属性
@Data
public class Car {
    private String type;
    private String color;
}

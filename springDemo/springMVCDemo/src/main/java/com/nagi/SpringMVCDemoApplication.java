package com.nagi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class SpringMVCDemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringMVCDemoApplication.class, args);
//        String[] beanDefinitionNames = run.getBeanDefinitionNames();
//        Arrays.asList(beanDefinitionNames).forEach(System.out::println);
    }
}

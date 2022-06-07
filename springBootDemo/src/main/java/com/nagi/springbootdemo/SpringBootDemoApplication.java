package com.nagi.springbootdemo;

import com.nagi.springbootdemo.entity.Car;
import com.nagi.springbootdemo.entity.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication //配置类，通过注解声明xml配置文件中的设置
@MapperScan("com.nagi.springbootdemo.mapper")
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringBootDemoApplication.class, args);
        String[] beanDefinitionNames = run.getBeanDefinitionNames();
        Arrays.asList(beanDefinitionNames).forEach(System.out::println);
        User nagi = run.getBean("nagi", User.class);
        User tom = run.getBean("myUser", User.class);
        Car car = run.getBean("car", Car.class);
        System.out.println(nagi + "\t" + tom + "\t" + car);
    }

}

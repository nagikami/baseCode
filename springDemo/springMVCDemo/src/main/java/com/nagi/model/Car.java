package com.nagi.model;

import com.nagi.validator.MyConstraint;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class Car {
    /**
     * JSR-303 注解声明验证约束，当使用JSR-303 Validator验证当前model实例时，约束生效
     * Validator可以作为spring bean配置到spring容器，当前使用org.hibernate.validator作为spring bean
     * 也可在配置类注册spring默认的LocalValidatorFactoryBean为Validator的spring bean
     */
    @NotNull
    private String type;
    private String color;
    @MyConstraint(11)
    private int price;
}

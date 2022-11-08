package com.nagi.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * ControllerAdvice用于controller增强，类似于controller的拦截器，在controller处理前后执行统一逻辑
 */
@ControllerAdvice
public class MyControllerAdvice {
    @ModelAttribute("global")
    public String handle() {
        return "hello global attr";
    }
}

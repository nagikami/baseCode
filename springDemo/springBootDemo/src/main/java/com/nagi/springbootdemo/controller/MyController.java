package com.nagi.springbootdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Locale;

@RestController
public class MyController {
    // 可通过handler method的特定参数类型声明HandlerAdapter回调handler method时传入的参数
    @GetMapping("/info")
    public String handle01(Locale locale) {
        return "hello nagi, current locale: " + locale.toString();
    }
}

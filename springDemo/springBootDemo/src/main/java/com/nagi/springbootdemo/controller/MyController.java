package com.nagi.springbootdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @GetMapping("/info")
    public String handle01() {
        return "hello nagi";
    }
}

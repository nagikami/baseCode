package com.nagi.springbootdemo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class MyController {
    @GetMapping("/info")
    public String handle01() {
        return "hello nagi";
    }
}

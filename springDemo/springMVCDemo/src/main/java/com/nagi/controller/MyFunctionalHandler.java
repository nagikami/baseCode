package com.nagi.controller;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * 定义FunctionalHandler，等同于注解式编程中的handler method
 */
public class MyFunctionalHandler {
    public ServerResponse getInfo(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body("info");
    }
}

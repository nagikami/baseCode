package com.nagi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.logging.Logger;

/**
 * ControllerAdvice用于controller增强，类似于controller的拦截器，在controller处理前后执行统一逻辑
 * 启动时，RequestMappingHandlerMapping和ExceptionHandlerExceptionResolver发现并加载@ExceptionHandler
 * 和@ModelAttribute、@InitBinder等注解的方法
 * 调用方法时@ControllerAdvice下的@ModelAttribute和@InitBinder全局方法早于@Controller下的同注解local方法
 * 而@ExceptionHandler则晚于@Controller下的同注解local方法
 */
@ControllerAdvice
public class MyControllerAdvice {

    private Logger logger = Logger.getLogger("myControllerAdvice");

    @ModelAttribute("global")
    public String handle() {
        return "hello global attr";
    }

    @ExceptionHandler
    public ResponseEntity<String> handle1(Exception e) {
        logger.info("global exception handler");
        return new ResponseEntity<>("exception handled", HttpStatus.OK);
    }
 }

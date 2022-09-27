package com.nagi.springbootdemo.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class HelloController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /**
         * 存在jsp等View时，构建mav，model为map保存页面变量
         * ModelAndView hello = new ModelAndView("hello");
         *         hello.addObject("message", "hello nagi");
         *         return hello;
         */
        PrintWriter writer = response.getWriter();
        writer.write("hello");
        return null;
    }
}

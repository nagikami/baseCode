package com.nagi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyCrossOrigin {
    /**
     * 简单请求，浏览器在request添加origin header，服务端返回的response带有Access-Control-Allow-Origin header
     * 浏览器检查请求是否符合服务端的访问控制，不符合则拒绝此请求
     * 复杂请求，浏览器发送方法为OPTIONS的预检（preflight）请求，携带Origin、Access-Control-Request-Method、Access-Control-Request-Headers headers
     * 服务端返回携带Access-Control-Allow-Origin、Access-Control-Allow-Methods、Access-Control-Allow-Headers等headers
     * 的response，浏览器检查请求是否符合服务端的访问控制，不符合则拒绝此请求，符合则发送正式的request，携带origin，
     * 服务端返回携带Access-Control-Allow-Origin的response，用以声明发生了CORS
     * @CrossOrigin 默认允许所有的origins、headers、RequestMapping指定的methods
     * allowCredentials则默认关闭，用以保护用户敏感信息，例如cookies和CSRF tokens，当启用时allowOrigins需设置明确的domain，不能是*
     * maxAge 默认设置为30 min
     * HandlerMapping会保存CorsConfiguration mappings（URL和访问控制策略的映射）
     */
    @CrossOrigin(origins = "http://127.0.0.1:8888")
    @RequestMapping("/cors")
    public String cors() {
        return "index";
    }
}

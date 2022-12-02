package com.nagi.controller;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

/**
 * HTTP caching可以提高web app的性能
 * 通过解析response的Cache-Control header和可选的request的Last-Modified、ETag（Edition Tag） headers，
 * 建议private（browser）和public（proxy）缓存如何缓存和复用response
 * 对于GET和HEAD请求设置response为304 (NOT_MODIFIED)，对于POST、PUT、DELETE请求则设置response为
 * 412 (PRECONDITION_FAILED)，以阻止并发修改
 */
@Controller
public class MyControllerWithHTTPCaching {
    /**
     * 服务端先计算lastModified或者ETag的值，再和request的If-None-Match或者If-Modified-Since header中保存的ETag或者lastModified的值做比较，
     * 如果相同则返回304 (NOT_MODIFIED)和空的body，相同则返回带有lastModified或者ETag的response和包含实际返回内容分的body
     */
    @RequestMapping("/cache")
    public ResponseEntity<String> cache(WebRequest request) {
        // 计算当前ETag
        String eTag = "v1";
        // ETag未改变，返回304 (NOT_MODIFIED)
        if (request.checkNotModified(eTag)) {
            return null;
        }

        // response在private cache缓存一小时
        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS);
        // 禁止缓存
        //CacheControl noStore = cacheControl.noStore();
        // response在private和public缓存一天，且public不转换response
        //CacheControl.maxAge(10, TimeUnit.DAYS).noTransform().cachePublic();
        return ResponseEntity
                .ok()
                .cacheControl(cacheControl)
                .eTag("v1")
                .body("index");
    }
}

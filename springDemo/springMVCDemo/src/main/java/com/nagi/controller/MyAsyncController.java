package com.nagi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.Callable;

@Controller
public class MyAsyncController {

    private DeferredResult<String> deferredResult;
    private ResponseBodyEmitter responseBodyEmitter;
    private SseEmitter sseEmitter;

    /**
     * controller返回DeferredResult并将其保存在队列或列表
     * Spring MVC调用request.startAsync()获取AsyncContext（保证在退出Servlet container线程后，response仍然open）
     * 与此同时，退出request的Servlet container处理线程
     * 其他线程为DeferredResult赋值，Spring MVC调用AsyncContext的dispatch方法将请求转发到Servlet container
     * DispatcherServlet再次被调用，用相同的URL匹配controller，但是不调用handler method，
     * 而是使用异步获取的返回值作为handler的return，继续处理从AsyncContext恢复的request，将结果写入open的response
     */
    @GetMapping("/async/hello")
    @ResponseBody
    public DeferredResult<String> hello() {
        DeferredResult<String> stringDeferredResult = new DeferredResult<>();
        this.deferredResult = stringDeferredResult;
        asyncProcess();
        return stringDeferredResult;
    }

    private void asyncProcess() {
        new Thread(() -> {
            if (deferredResult != null) {
                deferredResult.setResult("hello");
            }
        }).start();
    }

    @RequestMapping("/async/info")
    @ResponseBody
    public Callable<String> info() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "info";
            }
        };
    }

    /**
     * 使用ResponseBodyEmitter创建一个对象流，每个对象使用HttpMessageConverter序列化，并写入response
     */
    @GetMapping("/events")
    public ResponseBodyEmitter handle() {
        ResponseBodyEmitter responseBodyEmitter = new ResponseBodyEmitter();
        this.responseBodyEmitter = responseBodyEmitter;
        asyncPro();
        return responseBodyEmitter;
    }

    private void asyncPro() {
        new Thread(() -> {
            if (this.responseBodyEmitter != null) {
                try {
                    // 多次写入stream
                    this.responseBodyEmitter.send("hello");
                    this.responseBodyEmitter.send("nagi");
                    // 完成写入
                    this.responseBodyEmitter.complete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 返回SseEmitter（ResponseBodyEmitter的子类），提供对Server-Sent Events的支持，发送的event按照W3C SSE标准格式化
     */
    @GetMapping("/sse")
    public SseEmitter sseEmitter() {
        SseEmitter sseEmitter = new SseEmitter();
        this.sseEmitter = sseEmitter;
        asyncProSse();
        return sseEmitter;
    }

    private void asyncProSse() {
        new Thread(() -> {
            if (this.sseEmitter != null) {
                try {
                    this.sseEmitter.send("hello");
                    this.sseEmitter.send("world");
                    this.sseEmitter.complete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

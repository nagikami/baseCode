package com.nagi.config;

import com.nagi.controller.MyFunctionalHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.function.*;

import java.util.logging.Logger;

/**
 * Functional Endpoints配置类，使用函数式编程注册controller
 * RouterFunction的路由匹配，是从前往后，匹配到第一个路由则返回，而不是注解方式的匹配最符合的路由
 */
@Configuration
public class MyFunctionalConfiguration implements WebMvcConfigurer {

    private static Logger logger = Logger.getLogger("function");

    MyFunctionalHandler myFunctionalHandler = new MyFunctionalHandler();

    // 注册RouterFunction，声明请求和FunctionalHandler之间的映射
    @Bean
    public RouterFunction<?> routerFunction() {
        return RouterFunctions.route()
                .GET("/pre/hello", accept(MediaType.APPLICATION_JSON), myFunctionalHandler::getInfo)
                .POST("/pre/haha", accept(MediaType.APPLICATION_JSON), myFunctionalHandler::getInfo)
                .build();
    }

    // 添加RequestPredicate作为请求匹配条件，RequestPredicate也可以使用and、or组合条件
    public RequestPredicate accept(MediaType mediaType) {
        return RequestPredicates.contentType(mediaType);
    }

    // 使用嵌套（nested）路由共享path前缀，也可使用nest方法共享其他类型的predicate，例如Content-Type
    @Bean
    public RouterFunction<?> routerFunction1() {
        return RouterFunctions.route()
                .path("/pre1", builder -> {
                    builder.GET("/hello", accept(MediaType.APPLICATION_JSON), myFunctionalHandler::getInfo)
                            .GET("/haha", accept(MediaType.APPLICATION_JSON), myFunctionalHandler::getInfo);
                }).build();
    }

    private Boolean flag = true;

    /**
     * 过滤器只对当前builder生效，filter()一个参数是对request进行处理，第二个参数为执行链的下一个对象（可以是HandlerFunction，也可以是filter）
     */
    @Bean
    public RouterFunction<?> routerFunctionFilter() {
        return RouterFunctions.route()
                .path("/pre2", builder -> builder
                        .nest(accept(MediaType.APPLICATION_JSON), builder1 -> builder1.GET("/hello", myFunctionalHandler::getInfo)
                                .GET("/haha", myFunctionalHandler::getInfo))
                        .before(request -> {
                            logger.info("before filter: " + request.path());
                            return ServerRequest.from(request).build();
                        }))
                .filter((request, next) -> {
                    if (flag) {
                        return next.handle(request);
                    } else {
                        return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
                    }
                })
                .build();
    }
}

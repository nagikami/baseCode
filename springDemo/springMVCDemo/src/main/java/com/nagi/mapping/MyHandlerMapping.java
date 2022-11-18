package com.nagi.mapping;

import com.nagi.annotation.MyRequestMapping;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class MyHandlerMapping extends RequestMappingHandlerMapping {

    private Logger logger = Logger.getLogger("myHandlerMapping");

    /**
     * 自定义包含@RequestMapping的复合注解时可以添加自定义属性，在此处理自定义属性，并返回request匹配条件
     * 实现自定义request-mapping逻辑
     * @param method handler method
     */
    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        MyRequestMapping myRequestMapping = AnnotatedElementUtils.findMergedAnnotation(method, MyRequestMapping.class);
        if (myRequestMapping != null) {
            logger.info("myRequestMapping honored, fake is " + myRequestMapping.fake());
        }
        // 获取被MyRequestMapping组合的RequestMapping
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        if (requestMapping != null) {
            String values = "";
            for (String s : requestMapping.value()) {
                values += s;
            }
            logger.info("requestMapping values are " + values);
        }
        return new MyRequestCondition();
    }
}

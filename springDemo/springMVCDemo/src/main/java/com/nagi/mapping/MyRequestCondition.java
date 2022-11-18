package com.nagi.mapping;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

public class MyRequestCondition implements RequestCondition {
    @Override
    public Object combine(Object other) {
        return this;
    }

    @Override
    public Object getMatchingCondition(HttpServletRequest request) {
        return this;
    }

    @Override
    public int compareTo(Object other, HttpServletRequest request) {
        return 0;
    }
}

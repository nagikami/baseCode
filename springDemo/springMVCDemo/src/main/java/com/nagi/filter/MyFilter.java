package com.nagi.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class MyFilter implements Filter {

    private Logger logger = Logger.getLogger("myFilter");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // 记录请求
        logger.info("Current req: " + req.getRequestURI());
        // 调用下一个filter
        chain.doFilter(request, response);
        // 记录响应
        logger.info("Current headers of res: " + res.getHeaderNames());
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

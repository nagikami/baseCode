package com.nagi.springbootdemo.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

public class SaTokenTrackingFilter implements Filter {

    private static Logger logger = Logger.getLogger("saTokenTrackingFilter");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        logger.info("current url: " + req.getRequestURI());
        Cookie[] cookies = req.getCookies();
        StringBuilder cookieString = new StringBuilder();
        for (Cookie cookie : cookies) {
            cookieString.append("name: ").append(cookie.getName()).append("\n");
            cookieString.append("value: ").append(cookie.getValue()).append("\n");
            cookieString.append("max-age: ").append(cookie.getMaxAge()).append("\n");
        }
        logger.info(cookieString.toString());
        chain.doFilter(request, response);
        HttpServletResponse rep = (HttpServletResponse) response;
        Collection<String> headerNames = rep.getHeaderNames();
        StringBuilder headers = new StringBuilder();
        for (String headerName : headerNames) {
            headers.append(headerName).append(": ").append(rep.getHeader(headerName)).append("\t");
        }
        logger.info("response headers: " + headers);
    }
}

package com.nagi.springbootdemo.controller.satoken;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("session")
public class SessionManagerController {
    // 查询所有的tokenValue
    @RequestMapping("listToken")
    public List<String> listToken() {
        return StpUtil.searchTokenValue("", 0, -1, true);
    }

    // 查询User-Session的SessionId（account）
    @RequestMapping("listSession")
    public List<String> listSession() {
        return StpUtil.searchSessionId("", 0, -1, true);
    }

    @RequestMapping("listTokenSession")
    public List<String> listTokenSession() {
        return StpUtil.searchTokenSessionId("", 0, -1, true);
    }
}

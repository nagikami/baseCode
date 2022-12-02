package com.nagi.springbootdemo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyController {
    /**
     * 注解鉴权，通过拦截器实现
     * 登录校验：只有登录之后才能进入该方法
     */
    @SaCheckLogin
    @GetMapping("/info")
    public String handle01() {
        return "hello nagi";
    }

    /**
     * 注解式鉴权：只要具有其中一个权限即可通过校验
     * SaMode.AND, 标注一组权限，会话必须全部具有才可通过校验
     * SaMode.OR, 标注一组权限，会话只要具有其一即可通过校验
     * orRole, 权限认证未通过时的次要选择，两者只要其一认证成功即可通过校验
     */
    @SaCheckPermission(value = {"user.add", "user.list"}, mode = SaMode.OR, orRole = "admin")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    // User-Session：为每个账号id分配的session
    @RequestMapping("user-session")
    public String userSession() {
        // 获取当前账号id的Session (必须是登录后才能调用)
        SaSession session = StpUtil.getSession();
        session.set("user", "nagi");
        return session.getId();
    }

    // Token-Session：为每个token分配的session
    @RequestMapping("token-session")
    public String tokenSession() {
        // 获取当前 Token 的 Token-Session 对象
        SaSession tokenSession = StpUtil.getTokenSession();
        tokenSession.set("foo", "bar");
        return tokenSession.getId();
    }

    // 以一个特定值作为sessionId分配session
    @RequestMapping("custom-session")
    public String customSession() {
        SaSession session = SaSessionCustomUtil.getSessionById("key", true);
        session.set("nagi", "hello");
        return session.getId();
    }
}

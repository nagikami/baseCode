package com.nagi.springbootdemo.controller.satoken;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authority")
public class AuthorityController {
    @RequestMapping("permissions")
    public String permissions() {
        List<String> permissionList = StpUtil.getPermissionList();
        StringBuilder  permissions = new StringBuilder();
        for (String s : permissionList) {
            permissions.append(s);
        }
        return permissions.toString();
    }

    @RequestMapping("roles")
    public List<String> roles() {
        return StpUtil.getRoleList();
    }
}

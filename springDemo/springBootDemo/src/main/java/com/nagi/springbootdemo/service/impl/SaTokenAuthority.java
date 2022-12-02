package com.nagi.springbootdemo.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 自定义权限验证接口扩展
 */
@Component
public class SaTokenAuthority implements StpInterface {

    private final HashSet<String> accounts = new HashSet<>();

    {
        accounts.add("10001");
        accounts.add("10002");
        accounts.add("10003");
    }

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissions = new ArrayList<>();
        if (accounts.contains((String.valueOf(loginId)))) {
            permissions.add("user.add");
        }
        return permissions;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roles = new ArrayList<>();
        if (accounts.contains(String.valueOf(loginId))) {
            roles.add("admin");
        }
        return roles;
    }
}

package com.nagi.springbootdemo.controller.satoken;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/acc")
public class AuthenticateController {
    @RequestMapping("/doLogin")
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        if ("nagi".equals(username) && "nagi".equals(password)) {
            // 检查账号是否已封禁
            StpUtil.checkDisable("10001");
            // 会话登录：参数填写要登录的账号id，建议的数据类型：long | int | String， 不可以传入复杂类型，如：User、Admin 等等
            StpUtil.login("10001");
            return "login successfully!";
        }
        return "login failed, check username and password!";
    }

    @RequestMapping("/isLogin")
    public String isLogin() {
        return "current session is login: " + StpUtil.isLogin();
    }

    @RequestMapping("tokenInfo")
    public SaTokenInfo tokenInfo() {
        return StpUtil.getTokenInfo();
    }

    @RequestMapping("logout")
    public String logout() {
        // 账号注销，删除token信息，再次访问提示token无效
        StpUtil.logout();
        return "logout successfully!";
    }

    @RequestMapping("kickOut")
    public String kickOut() {
        // 踢人下线，标记但不清除token信息，再次访问提示token已被踢下线
        StpUtil.kickout("10001");
        return "kick out successfully!";
    }

    @RequestMapping("loginTrack")
    public Cookie[] loginTrack(HttpServletRequest request) {
        return request.getCookies();
    }

    // 使用二级认证，也可以通过@SaCheckSafe实现
    @RequestMapping("deleteProject")
    public String deleteProject() {
        // 检查当前会话是否已完成二级认证
        if (!StpUtil.isSafe("module1")) {
            return "Please complete second level authentication";
        }
        // do delete project
        return "Delete successfully!";
    }

    @RequestMapping("openSafe")
    public String openSafe(@RequestParam("password") String password) {
        if ("nagi".equals(password)) {
            // 为当前会话打开二级认证，服务名为module1，有效期为120秒
            StpUtil.openSafe("module1", 120);
            return "Open second level authentication";
        }
        return "Second level authentication failed";
    }

    //  临时身份切换
    @RequestMapping("doSwitch")
    public String accSwitch() {
        // 身份切换，当前request有效
        StpUtil.switchTo("10002");
        // do something
        StpUtil.endSwitch();

        // 自动关闭Switch
        StpUtil.switchTo("10002", () -> {
            //do something
        });
        return "Success!";
    }

    // 账号封禁，可以按类型、等级封禁，也可以通过@SaCheckDisable实现
    @RequestMapping("disable")
    public String disable() {
        // 立即下线
        StpUtil.kickout("10001");
        // 封禁120s
        StpUtil.disable("10001", 120);
        return "account is disabled";
    }

    // 摘要
    @RequestMapping("digest")
    public String digest() {
        String text = "123456";
        // MD5
        return "value: " + text + " digest: " + SaSecureUtil.md5(text);
    }

    // 对称加密
    @RequestMapping("encryption")
    public String encryption() {
        String text = "123456";
        String key = "123456";
        // AES加密
        return "value: " + text + " data encryption: " + SaSecureUtil.aesEncrypt(key, text);
    }

    // 非对称加密
    @RequestMapping("rsaEncrypt")
    public String rsaEncrypt() throws Exception {
        String text = "123456";
        HashMap<String, String> stringStringHashMap = SaSecureUtil.rsaGenerateKeyPair();
        // 私钥签名
        return "value: " + text + " data encryption: " + SaSecureUtil.rsaEncryptByPrivate(stringStringHashMap.get("private"), text);
    }
}

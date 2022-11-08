package com.nagi.springbootdemo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nagi.springbootdemo.model.User;
import com.nagi.springbootdemo.mapper.UserMapper;
import com.nagi.springbootdemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringBootDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    public void selectList() {
        userMapper.selectList(null).forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        User user = new User(null, "nagi", 100d, "test", 19, 1);
        int result = userMapper.insert(user);
        System.out.println(result);
        System.out.println("id: " + user.getId());
    }

    @Test
    public void count() {
        System.out.println(userService.count());
    }

    @Test
    public void testQueryWrapper() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("name", "a")
                .between("age", 10, 20)
                .isNotNull("salary");
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testCondition() {
        String userName = "a";
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like(StringUtils.isNotBlank(userName),"name", "a")
                .between("age", 10, 20)
                .isNotNull("salary");
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testLambda() {
        String userName = "a";
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.like(StringUtils.isNotBlank(userName), User::getName, "a")
                .between(User::getAge, 10, 20);
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        System.out.println(users);
    }

    @Test
    public void testPage() {
        Page<User> userPage = new Page<>(1, 5);
        userMapper.selectPage(userPage, null);
        List<User> records = userPage.getRecords();
        records.forEach(System.out::println);
        System.out.println("total: " + userPage.getTotal());
        System.out.println("previous: " + userPage.hasPrevious());
        System.out.println("next: " + userPage.hasNext());
    }
}

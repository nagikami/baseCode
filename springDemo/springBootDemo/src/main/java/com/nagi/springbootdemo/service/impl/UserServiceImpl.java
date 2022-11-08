package com.nagi.springbootdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nagi.springbootdemo.model.User;
import com.nagi.springbootdemo.mapper.UserMapper;
import com.nagi.springbootdemo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

}

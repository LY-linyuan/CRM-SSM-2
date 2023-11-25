package com.onlyone.crm.settings.service.impl;

import com.onlyone.crm.settings.domain.User;
import com.onlyone.crm.settings.mapper.UserMapper;
import com.onlyone.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @Author 临渊
 * @Date 2023-11-24 19:53
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserByLoginActAndLoginPwd(Map<String, Object> map) {
        return userMapper.selectUserByLoginActAndLoginPwd(map);
    }
}

package com.onlyone.crm.settings.service;

import com.onlyone.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User selectUserByLoginActAndLoginPwd(Map<String, Object> map);

    List<User> selectAllNoLockStateUser();

}

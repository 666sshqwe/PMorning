package com.onlinetea.prower.Service;

import com.onlinetea.prower.Bean.UserInfo;

public interface UserInfoService {

    /**
     * 根据用户名查询用户信息
     *
     * */
    UserInfo queryUserInfoByName(String useName);

    /**
     * 用户注册
     *
     * */
    int logonUser(UserInfo user);

    /**
     * 根据用户ID查询用户信息
     *
     * */
    UserInfo queryUserInfoById(String userId);
}

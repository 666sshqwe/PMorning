package com.onlinetea.prower.Service.impl;

import com.onlinetea.prower.Bean.UserInfo;
import com.onlinetea.prower.Service.UserInfoService;
import com.onlinetea.prower.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public UserInfo queryUserInfoByName(String useName) {
        return userInfoMapper.queryUserInfoByName(useName);
    }

    @Override
    public int logonUser(UserInfo user) {
        return userInfoMapper.logonUser(user);
    }

    @Override
    public UserInfo queryUserInfoById(String userId) {
        return userInfoMapper.queryUserInfoById(userId);
    }
}

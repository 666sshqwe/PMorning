package com.onlinetea.prower.Service.impl;

import com.alibaba.fastjson.JSONObject;
import com.onlinetea.prower.Bean.UserInfo;
import com.onlinetea.prower.Service.LoginService;
import com.onlinetea.prower.Service.UserInfoService;
import com.onlinetea.prower.View.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserInfoService userInfoService;

    @Override
    public JSONObject doLogin(UserInfo user) {
        JSONObject result= new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getUserPassword());
        try{
            subject.login(token);
            UserInfo userInfo = userInfoService.queryUserInfoByName(user.getUserName());
            result.put("status","success");
            result.put("userInfo",mapUserVo(userInfo));
        }catch (Exception e){
            log.warn("登录时报错 "+e.getMessage());
            result.put("status","error");
            result.put("userInfo","");
        }
        return result;
    }

    @Override
    public JSONObject doLogon(UserInfo user) {
        JSONObject result= new JSONObject();
        user.setCreateTime(LocalDateTime.now());
        user.setCType("1");
        user.setSaltUuid(UUID.randomUUID().toString());
        try{
            userInfoService.logonUser(user);
            UserInfo userInfo = userInfoService.queryUserInfoByName(user.getUserName());
            result.put("status","success");
            result.put("userInfo",mapUserVo(userInfo));
        }catch (Exception e){
            log.warn("注册时报错 "+e.getMessage());
            result.put("status","fail");
            result.put("userInfo","");
        }
        return result;
    }

    /**拼装前端需要的用户信息
     * 暂定一些需要的字段
     *
     * */
    private UserInfoVo mapUserVo(UserInfo user){
        UserInfoVo userInfo = new UserInfoVo();
        userInfo.setCId(user.getCId());
        userInfo.setUserName(user.getUserName());
        userInfo.setUserSex(user.getUserSex());
        userInfo.setCreateTime(user.getCreateTime());
        return userInfo;
    }

}

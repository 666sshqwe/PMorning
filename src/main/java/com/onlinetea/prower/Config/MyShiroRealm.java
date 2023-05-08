package com.onlinetea.prower.Config;

import com.onlinetea.prower.Bean.UserInfo;
import com.onlinetea.prower.Config.LoginConfig.JwtToken;
import com.onlinetea.prower.Service.UserInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    UserInfoService userInfoService;


    /**角色权限和对应权限添加
     * 将数据库中角色和权限授权给输入的用户名
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String users = token.getUsername();
        UserInfo userInfo =  userInfoService.queryUserInfoByName(users);
        if(userInfo == null){
            return null;
        }
        return new SimpleAuthenticationInfo(users,userInfo.getUserPassword(),getName());
    }

// 密码加密登录校验
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        String userName;
//
//        // 有token，则说明这是第一次登录成功了，携带token访问
//        if(authenticationToken instanceof JwtToken){
//            JwtToken token = (JwtToken)authenticationToken;
//            userName = token.getPrincipal().toString();
//            UserInfo userInfo =  userInfoService.queryUserInfoByName(userName);
//            return new SimpleAuthenticationInfo();
//        }else{ // 没有token，则是第一次登录，需要向shiro中放入账号密码，加密方法，盐值
//            UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
//            String users = token.getUsername();
//            UserInfo userInfo =  userInfoService.queryUserInfoByName(users);
//            return new SimpleAuthenticationInfo(users,userInfo.getUserPassword(), ByteSource.Util.bytes(""),getName());
//        }
//    }
}

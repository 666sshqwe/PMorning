package com.onlinetea.prower.Config.LoginConfig;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;


/**
 * 放弃shiro使用的AuthenticationToken，也就是realm中用到的
 * 改用自己修改过的token
 *
 * */
@Data
public class JwtToken implements AuthenticationToken {

    private String token;


   public JwtToken(String token){
       this.token = token;
   }


    @Override
    public Object getPrincipal() {
        return JwtUtil.getUsername(token);
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

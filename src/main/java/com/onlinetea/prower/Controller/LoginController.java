package com.onlinetea.prower.Controller;

import com.alibaba.fastjson.JSONObject;
import com.onlinetea.prower.Bean.UserInfo;
import com.onlinetea.prower.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * 登录
     *
     * */
    @PostMapping("/login")
    public JSONObject loginStory(@RequestBody UserInfo user) {
        return loginService.doLogin(user);
    }


    /**
     * 注册
     *
     * */
    @PostMapping("/logon")
    public JSONObject logonStory(@RequestBody UserInfo user) {
        return loginService.doLogon(user);
    }

}

package com.onlinetea.prower.Controller;

import com.onlinetea.prower.Bean.UserInfo;
import com.onlinetea.prower.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/userInfo")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;


    /**
     * 根据用户ID查询用户信息
     *
     * */
    @GetMapping("/qUserById")
    public UserInfo qUserById(@RequestParam String useId){
      return  userInfoService.queryUserInfoById(useId);
    }


    /**
     * 根据用户ID查询用户信息
     *
     * */
    @GetMapping("/qUserByName")
    public UserInfo qUserByName(@RequestParam String name){
        return  userInfoService.queryUserInfoByName(name);
    }


}

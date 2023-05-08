package com.onlinetea.prower.Controller;

import com.onlinetea.prower.Uitls.WebUtisServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/pageData")
public class MainPageController {

    @Autowired
    static
    WebUtisServ webUtisServ;

    /**获取用户发送的信息*/
    @RequestMapping(value = "/mainpage",method =RequestMethod.POST)
    @ResponseBody
    public void doPost(HttpServletRequest request,HttpServletResponse response){

        System.out.println("dopost！！！");
    }

}

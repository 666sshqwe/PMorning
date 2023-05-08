package com.onlinetea.prower.TestController;

import com.alibaba.fastjson.JSONObject;
import com.onlinetea.prower.Bean.SessionsInfo;
import com.onlinetea.prower.TestController.service.UyunService;
import com.onlinetea.prower.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/yun")
public class UyunController {

    @Autowired
    UyunService uyunService;

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/geti6000")
    public String reset() {
        return Constants.I6OOODATA;
    }



    //http://localhost:8015/stores/yun/reset?code=T10102
    @GetMapping("/reset")
    public JSONObject reset(@RequestParam String code) {
        redisTemplate.opsForValue().set("123","123");
//        JSONObject data =  uyunService.dealData(code);
        return null;
    }





}

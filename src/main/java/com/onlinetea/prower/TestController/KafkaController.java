package com.onlinetea.prower.TestController;


import com.alibaba.fastjson.JSONObject;
import com.onlinetea.prower.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/yun")
public class KafkaController {

    @Autowired
    private KafkaTemplate kafkaTemplate;


    @GetMapping("/produce")
    public String produce(@RequestParam String topic) {
        kafkaTemplate.send("test.send",0,"1012","测试一下发送信息看看2");
        kafkaTemplate.send("test.send","测试一下发送信息看看2");
        kafkaTemplate.send("test.send","测试一下发送信息看看3");

        return "";
    }



    //http://localhost:8015/stores/yun/reset?code=T10102
    @GetMapping("/consumer")
    public String consumer(@RequestParam String code) {

        return "ok";
    }

}

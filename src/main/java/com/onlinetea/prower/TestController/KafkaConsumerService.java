package com.onlinetea.prower.TestController;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaConsumerService {

//    @KafkaListener(topics = {"test.send"},groupId = "lhr-group",errorHandler="consumerAwareErrorHandler")
//    public void message1(ConsumerRecords<?, ?> record){
//        Map<String,JSONObject> itemI6000 = new HashMap<>();
//        // 消费的哪个topic、partition的消息,打印出消息内容
//        for(ConsumerRecord<?, ?> consumerRecord : record){
//
//            JSONObject value = JSONObject.parseObject(consumerRecord.value().toString());
//            itemI6000.put(value.getString("CI_ID"),value);
//            String cTypeId = value.getString("CITYPE_ID");
//        }
//    }


}

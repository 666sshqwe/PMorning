package com.onlinetea.prower.TestController;

import org.apache.kafka.clients.consumer.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

public class MyConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"172.40.244.55:9192");
        //消费者组名
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"lhr-group");

        //是否自动提交offset,默认是true
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"ture");
        //自动提交offset间隔时间
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");

        /**1、消费速度相关参数
         */
        //一次poll拉取消息的最大条数
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,"500");



        /** 2、心跳设置
         */
        //consumer给broker发送消息时间
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,"1000");
        //kafka如果超过10s没有收到消息的心跳,则会把消费者提出消费者组,进行rebalance,将分区分配给其他消费者
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"10 * 1000");



        /**3、新消费者组如何消费
         * ①latest：只消费自己启动之后发送到主题的时间
         * ②earlist：第一次从头开始消费，以后按照offset记录继续消费，这个区别于Consumer.seekToBegining(每次都是从头开始消费）
         */
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,"30 * 1000");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        Consumer<String,String> consu = new KafkaConsumer<String,String>(props);
        Collection<String> topics = Arrays.asList("test.send");
        //消费者订阅topic
        consu.subscribe(topics);
        ConsumerRecords<String,String> consumerRecords = null;
        while(true){
            //接下来就要从topic中拉去数据
            consumerRecords = consu.poll(Duration.ofMillis(1000));
            //遍历每一条记录
//            for(ConsumerRecord<String, String> consumerRecord : consumerRecords){
//                long offset = consumerRecord.offset();
//                int partition = consumerRecord.partition();
//                Object key = consumerRecord.key();
//                Object value = consumerRecord.value();
//                System.out.println(offset+" "+partition+" "+key+" "+value);
//            }
        }
    }

}

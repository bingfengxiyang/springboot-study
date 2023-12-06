package com.kafkatemplete.show;



import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@Slf4j
public class ProductController {

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;





    public static final  String key = "testTopic";

    @GetMapping("/sendSimple")
    public String sendSimple() {
        //发送消息
        String topic = "testTopic";
        String key = "testSimple";
        String data = "testSimple";
        kafkaTemplate.send(topic, key, data);
        return "success";
    }

//    @GetMapping("/create_topic")
//    public String createTopic(String topicName) {
//        // 创建topic 3个分区 2个副本
//        NewTopic topic = new NewTopic(topicName, 8, (short) 3);
//        kafkaAdmin.createOrModifyTopics(topic);
//        return "success";
//    }
    @GetMapping("/sendOpearSimple")
    public String sendOpearSimple() {
        //发送消息
        kafkaTemplate.send("sendOpearSimple1", "testTopicopear", "testTopicopear");
        log.info("生产者发送消息成功");
        return "success";
    }

    @GetMapping("/sendCallBack")
    public String sendCallBack() {
        //发送消息
       kafkaTemplate.send("sendOpearSimple1","测试回调").addCallback(
               success -> {
                   String topic = success.getRecordMetadata().topic();
                   int partition = success.getRecordMetadata().partition();
                   long offset = success.getRecordMetadata().offset();
                   log.info("发送消息成功：{}-{}-{}", topic, partition, offset);
               },
               failure -> {
                   log.error("发送消息失败：{}", failure.getMessage());
               }
         );
        log.info("生产者发送消息成功");

        return "success";
    }

    @GetMapping("/sendTransactionMessage")
    public String sendTransactionMessage() {
        //发送消息
        kafkaTemplate.executeInTransaction(operations -> {
            operations.send("tx", "tx", "测试事务");
            throw new RuntimeException("fail");
        });
        kafkaTemplate.send("sb_topic", "tx" + " test executeInNoTransaction");
        log.info("生产者发送消息成功");
        return "success";
    }
    @GetMapping("/sendtopic6")
    public String sendtopic6() {
        //发送消息
        kafkaTemplate.send("sendtopic6", "sendtopic1", "sendtopic1");
        log.info("生产者发送消息成功");
        return "success";
    }
    @GetMapping("/sendtopic1")
    public String sendtopic1() {
        //发送消息
        kafkaTemplate.send("sendtopic1", "sendtopic1", "sendtopic1");
        log.info("生产者发送消息成功");
        return "success";
    }
    // 指定分区
    @GetMapping("/sendtopicpartions")
    public String sendtopicpartions() {
        //发送消息
        kafkaTemplate.sendDefault(0,"sendtopicpartions", "sendtopicpartions");
        log.info("生产者发送消息成功");
        return "success";
    }


}

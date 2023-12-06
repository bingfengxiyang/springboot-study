package com.kafkatemplete.show;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ConsumerShow {

@Autowired
private KafkaTemplate<String, Object> kafkaTemplate;


    @KafkaListener(topics = "testTopic")
    public void listen(ConsumerRecord<String, Object> record) {
      log.info("消费  topic = {}, offset = {}, value = {}", record.topic(), record.offset(), record.value());
    }
    @KafkaListener(topics = "sendOpearSimple1")
    public void sendOpearSimple(ConsumerRecord<String, Object> record) {
        log.info(" 消费  topic = {}, offset = {}, value = {}", record.topic(), record.offset(), record.value());
    }
    @KafkaListener(id = "batch",groupId = "felix-group", topics = "batch")
    public void onMessage3(List<ConsumerRecord<?, ?>> records) {
        System.out.println(">>>批量消费一次，records.size()="+records.size());
        for (ConsumerRecord<?, ?> record : records) {
            log.info("批量消费  topic = {}, offset = {}, value = {}", record.topic(), record.offset(), record.value());
        }
    }
    // 消息过滤监听
    @KafkaListener(topics = {"messageListener"}, containerFactory = "kafkaListenerContainerFactory")
    public void onMessage6(ConsumerRecord<?, ?> record) {
       log.info("过滤消费  topic = {}, offset = {}, value = {}", record.topic(), record.offset(), record.value());
    }


    /**
     * 从topic1接收到的消息经过处理后转发到topic2
     * @param record
     * @return
     */
    @KafkaListener(topics = {"sendtopic1"})
    @SendTo("sendtopic2")
    public String sendtopic1(ConsumerRecord<?, ?> record) {
        return record.value()+"-forward message";
    }
    @KafkaListener(topics = {"sendtopic2"})
    public String sendtopic2(ConsumerRecord<?, ?> record) {
        return record.value()+"-forward message";
    }
    @KafkaListener(topics = "sendtopic6")
    public String sendtopic6(ConsumerRecord<?, ?> record) {
        log.info("sendtopic6  topic = {}, offset = {}, value = {}", record.topic(), record.offset(), record.value());
        return record.value()+"-forward message";
    }
    /**
     * 指定topic、partition、offset消费
     * 同时监听topic1和topic2，监听topic1的0号分区、topic2的 "0号和1号" 分区，指向1号分区的offset初始值为8
     * @param record
     *
     */
    @KafkaListener(id = "consumer", errorHandler = "myErrorHandler",topicPartitions = {
            @TopicPartition(topic = "testTopicopear", partitions = { "0" }),
            @TopicPartition(topic = "testTopicopear", partitions = "0", partitionOffsets =@PartitionOffset(partition = "1", initialOffset = "1"))
    })
    public void onMessage2(ConsumerRecord<?, ?> record) {
        log.info("topic1的0号分区，topic2的0号分区的0号分区消费  topic = {}, offset = {}, value = {}", record.topic(), record.offset(), record.value());
    }
    //消费分区制定
    @KafkaListener(topics = {"sendtopicpartions"})
    public void sendtopicpartions(ConsumerRecord<?, ?> record) {

        log.info("消费分区制定  topic = {}, offset = {}, value = {}", record.topic(), record.offset(), record.value());
    }


}

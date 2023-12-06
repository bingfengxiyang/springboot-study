package com.kafkatemplete.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer2 {


    @Autowired
    ConsumerFactory consumerFactory;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setReplyTemplate(kafkaTemplate);
        factory.setConsumerFactory(consumerFactory);
        // 被过滤的消息将被丢弃
        factory.setAckDiscarded(true);
        // 消息过滤策略
//        factory.setRecordFilterStrategy(consumerRecord -> {
//            // 过滤奇数
//
////            if (Integer.parseInt(consumerRecord.value().toString()) % 2 == 0) {
////                return false;
////            }
//            //返回true消息则被过滤
//            return true;
//        });
        return factory;
    }

}

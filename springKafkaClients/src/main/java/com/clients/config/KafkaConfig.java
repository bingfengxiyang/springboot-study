package com.clients.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;



    @Bean
    public KafkaProducer kafkaProducer(){
        Map<String,Object> configs=new HashMap<>();
        configs.put("bootstrap.servers",bootstrapServers);
        configs.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        configs.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        configs.put("acks","all");
        configs.put("retries",0);
        configs.put("batch.size",16384);
        configs.put("buffer.memory",33554432);
        return new KafkaProducer(configs);
    }
    @Bean
    public KafkaConsumer kafkaConsumer(){
        Map<String,Object> configs=new HashMap<>();
        configs.put("bootstrap.servers",bootstrapServers);
        configs.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        configs.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        configs.put("group.id","test");
        configs.put("auto.offset.reset","earliest");
        configs.put("enable.auto.commit","true");
        configs.put("auto.commit.interval.ms","1000");
        return new KafkaConsumer(configs);
    }
}

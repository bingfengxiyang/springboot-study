package com.clients.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@Slf4j
public class ConsumerClients implements  ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private KafkaConsumer kafkaConsumer;



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Collection<String> topics =new ArrayList<>();
        topics.add("producer1");
        kafkaConsumer.subscribe(topics);
        while (true){
            ConsumerRecords<String,String> poll = kafkaConsumer.poll(1000);
            for (ConsumerRecord<String,String> consumerRecords:poll){
                String value = consumerRecords.value();
                log.info("value = {}",value);
                log.info("consumerRecords = {}",consumerRecords);
            }
        }


        // 指定 时间点消费 1小时前的数据
//        kafkaConsumer.subscribe(Collections.singleton("producer1"));
//        Set<TopicPartition> assignment = new HashSet<>();
//        while (assignment.size() == 0) {
//            kafkaConsumer.poll(Duration.ofMillis(100));
//            assignment = kafkaConsumer.assignment();
//        }
//        Map<TopicPartition, Long> timestampToSearch = new HashMap<>();
//        for (TopicPartition tp : assignment) {
//            timestampToSearch.put(tp, System.currentTimeMillis() - 1 * 3600 * 1000);
//        }
//        Map<TopicPartition, OffsetAndTimestamp> offsets = kafkaConsumer.offsetsForTimes(timestampToSearch);
//        for (TopicPartition tp : assignment) {
//            OffsetAndTimestamp offsetAndTimestamp = offsets.get(tp);
//            if (offsetAndTimestamp != null) {
//                kafkaConsumer.seek(tp, offsetAndTimestamp.offset());
//            }
//        }
////        while (true) {
//            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
//            for (TopicPartition partition : records.partitions()) {
//                List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
//                for (ConsumerRecord<String, String> record : partitionRecords) {
//                    log.info("消费  partition = {},offset = {},value = {}", record.partition(), record.offset(), record.value());
//                }
//                long lastConsumedOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
//                //将消费位移存储在DB中
//                log.info("partition = {},lastConsumedOffset = {}", partition, lastConsumedOffset);
//            }
//        }
    }
}

package com.kafkatemplete.config;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class CustomizePartitioner implements Partitioner {
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        //自定义分区规则，默认全部发送到0号分区

        //自定义分区
        if (o1.toString().equals("0")) {
            return 0;
        }else if (o1.toString().equals("1")) {
            return 1;
        }else{
            return 2;
        }

    }

    @Override
    public void close() {

    }



    @Override
    public void configure(Map<String, ?> map) {

    }
}
package com.kafkatemplete.exp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyErrorHandler implements KafkaListenerErrorHandler {
    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException e) {
        // 处理异常，例如打印错误日志、发送错误消息等,自定义逻辑处理
        log.error("Error occurred while processing message:  {}", e.getMessage());
        return null;
    }
}
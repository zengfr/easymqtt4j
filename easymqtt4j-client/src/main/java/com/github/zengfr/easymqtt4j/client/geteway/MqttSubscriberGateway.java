package com.github.zengfr.easymqtt4j.client.geteway;

import org.springframework.messaging.Message;

import java.util.UUID;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
public interface MqttSubscriberGateway {
    boolean handlerMqttMessage(Message<?> msg, String topic, Integer qos,
                               Boolean retained, UUID id, Long timestamp);
}

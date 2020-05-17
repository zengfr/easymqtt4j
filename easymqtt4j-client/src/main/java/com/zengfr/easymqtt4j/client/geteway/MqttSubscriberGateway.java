package com.zengfr.easymqtt4j.client.geteway;

import org.springframework.messaging.Message;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
public interface MqttSubscriberGateway {
    boolean handlerMqttMessage( Message<?> msg,String topic, String qos,
                                String id, String timestamp);
}

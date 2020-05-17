package com.zengfr.easymqtt4j.client.geteway;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttPublisherGateway {
    void publish(String payload);

    void publish(@Header(MqttHeaders.QOS) int qos, String payload);

    void publish(@Header(MqttHeaders.TOPIC) String topic, String payload);

    void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);

    void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, @Header(MqttHeaders.RETAINED) boolean  retained, String payload);
}

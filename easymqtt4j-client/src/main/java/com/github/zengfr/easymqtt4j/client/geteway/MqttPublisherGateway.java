package com.github.zengfr.easymqtt4j.client.geteway;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttPublisherGateway {
    <T> void publish(@Headers Map<String, Object> map, @Payload T payload);

    <T> void publish(@Header(MqttHeaders.QOS) int qos, @Headers Map<String, Object> map, @Payload T payload);

    <T> void publish(@Header(MqttHeaders.TOPIC) String topic, @Headers Map<String, Object> map, @Payload T payload);

    <T> void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, @Headers Map<String, Object> map, @Payload T payload);

    <T> void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, @Header(MqttHeaders.RETAINED) boolean retained, @Headers Map<String, Object> map, @Payload T payload);

    <T> void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, @Header(MqttHeaders.RETAINED) boolean retained, @Header(MqttHeaders.DUPLICATE) boolean duplicate, @Headers Map<String, Object> map, @Payload T payload);


    <T> void publish(@Payload T payload);

    <T> void publish(@Header(MqttHeaders.QOS) int qos, @Payload T payload);

    <T> void publish(@Header(MqttHeaders.TOPIC) String topic, @Payload T payload);

    <T> void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, @Payload T payload);

    <T> void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, @Header(MqttHeaders.RETAINED) boolean retained, @Payload T payload);

    <T> void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, @Header(MqttHeaders.RETAINED) boolean retained, @Header(MqttHeaders.DUPLICATE) boolean duplicate, @Payload T payload);


    void publish(@Payload String payload);

    void publish(@Header(MqttHeaders.QOS) int qos, @Payload String payload);

    void publish(@Header(MqttHeaders.QOS) int qos, @Header(MqttHeaders.RETAINED) boolean retained, @Payload String payload);

    void publish(@Header(MqttHeaders.QOS) int qos, @Header(MqttHeaders.RETAINED) boolean retained, @Header(MqttHeaders.DUPLICATE) boolean duplicate, String payload);

    void publish(@Header(MqttHeaders.TOPIC) String topic, String payload);

    void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);

    void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, @Header(MqttHeaders.RETAINED) boolean retained, String payload);

    void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, @Header(MqttHeaders.RETAINED) boolean retained, @Header(MqttHeaders.DUPLICATE) boolean duplicate, String payload);

}

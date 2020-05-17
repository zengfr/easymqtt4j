package com.zengfr.easymqtt4j.client.geteway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageHandler;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
@Configuration
@IntegrationComponentScan
public class MqttSubscriberHandler {
    static Logger logger = LoggerFactory.getLogger(MqttSubscriberHandler.class);
    public static final String RECEIVE_TOPIC = MqttHeaders.RECEIVED_TOPIC;
    public static final String RECEIVE_QOS = MqttHeaders.RECEIVED_QOS;
    @Autowired
    MqttSubscriberGateway inboundGateway;
    @Bean
    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public MessageHandler mqttInboundMessageHandler() {
        return message -> {
            logger.debug(String.format("inbound:%s", message));
            String id = message.getHeaders().getId().toString();
            String timestamp = message.getHeaders().getTimestamp().toString();
            String topic = message.getHeaders().get(RECEIVE_TOPIC).toString();
            String qos = message.getHeaders().get(RECEIVE_QOS).toString();
            //String payload = message.getPayload().toString();
            inboundGateway.handlerMqttMessage(message, topic, qos, id, timestamp);
        };
    }
}

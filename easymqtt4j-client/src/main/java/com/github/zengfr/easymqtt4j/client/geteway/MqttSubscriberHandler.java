package com.github.zengfr.easymqtt4j.client.geteway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageHandler;

import java.util.UUID;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
@Configuration
@IntegrationComponentScan
public class MqttSubscriberHandler {
    static Logger logger = LoggerFactory.getLogger(MqttSubscriberHandler.class);

    @Autowired
    MqttSubscriberGateway inboundGateway;

    @Bean
    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public MessageHandler mqttInboundMessageHandler() {
        return message -> {
            logger.debug(String.format("inbound:%s", message));
            UUID id = message.getHeaders().getId();
            Long timestamp = message.getHeaders().getTimestamp();
            String topic = message.getHeaders().getOrDefault(MqttHeaders.RECEIVED_TOPIC,"").toString();
            Integer qos = Integer.valueOf( message.getHeaders().getOrDefault(MqttHeaders.RECEIVED_QOS,"0").toString());
            Boolean retained =Boolean.valueOf(message.getHeaders().getOrDefault(MqttHeaders.RECEIVED_RETAINED,"false").toString());
            inboundGateway.handlerMqttMessage(message, topic, qos, retained, id, timestamp);
        };
    }
}

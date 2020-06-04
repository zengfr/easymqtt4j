package com.github.zengfr.easymqtt4j.test.case1;


import com.github.zengfr.easymqtt4j.client.geteway.MqttEventGateway;
import com.github.zengfr.easymqtt4j.client.util.MqttMsgUtil;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.integration.mqtt.event.MqttIntegrationEvent;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
@Component
public class MqttEventGatewayImpl implements MqttEventGateway {
    static Logger logger = LoggerFactory.getLogger(MqttEventGatewayImpl.class);

    @Override
    public void handleEvent(MqttIntegrationEvent event) {

        logger.info("event:               {}", event);
    }

    @Override
    public void handleEvent(ApplicationEvent event) {

        logger.info("event:               {}", event);
    }

    @Override
    public void connectionLost(String clientId, Throwable cause) {

        logger.info("connectionLost:      {} {}", clientId, cause);
    }

    @Override
    public void messageArrived(String clientId, String topic, MqttMessage message) {
        logger.info("messageArrived:      {} {} {}", clientId, topic, message);
    }

    @Override
    public void deliveryComplete(String clientId, IMqttDeliveryToken token) {
        try {
            logger.info("deliveryComplete:    {} {}", clientId, MqttMsgUtil.tokenToString(token));
        } catch (MqttException e) {
            logger.error("error", e);
        }
    }

    @Override
    public void preSend(String clientId, Message<?> message, MessageChannel channel) {

    }

    @Override
    public void postSend(String clientId, Message<?> message, MessageChannel channel, boolean sent) {

    }

    @Override
    public void afterSendCompletion(String clientId, Message<?> message, MessageChannel channel, boolean sent, Exception ex) {

    }

    @Override
    public boolean preReceive(String clientId, MessageChannel channel) {
        return false;
    }

    @Override
    public Message<?> postReceive(String clientId, Message<?> message, MessageChannel channel) {
        return null;
    }

    @Override
    public void afterReceiveCompletion(String clientId, Message<?> message, MessageChannel channel, Exception ex) {

    }
}

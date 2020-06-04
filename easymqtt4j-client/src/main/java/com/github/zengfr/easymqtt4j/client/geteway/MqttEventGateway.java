package com.github.zengfr.easymqtt4j.client.geteway;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.ApplicationEvent;
import org.springframework.integration.mqtt.event.MqttIntegrationEvent;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
public interface MqttEventGateway {
    void handleEvent(MqttIntegrationEvent event);

    void handleEvent(ApplicationEvent event);

    void connectionLost(String clientId, Throwable cause);

    void messageArrived(String clientId, String topic, MqttMessage message);

    void deliveryComplete(String clientId, IMqttDeliveryToken token);

    void preSend(String clientId, Message<?> message, MessageChannel channel);

    void postSend(String clientId, Message<?> message, MessageChannel channel, boolean sent);

    void afterSendCompletion(String clientId,
                             Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex);

    boolean preReceive(String clientId, MessageChannel channel);


    Message<?> postReceive(String clientId, Message<?> message, MessageChannel channel);


    void afterReceiveCompletion(String clientId, @Nullable Message<?> message, MessageChannel channel,
                                @Nullable Exception ex);
}

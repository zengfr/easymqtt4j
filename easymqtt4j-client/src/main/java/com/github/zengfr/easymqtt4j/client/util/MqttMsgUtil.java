package com.github.zengfr.easymqtt4j.client.util;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
public class MqttMsgUtil {
    public static <T> MessageBuilder<T> getMessageBuilder(T content) {

        return MessageBuilder.withPayload(content);
    }

    public static <T> MessageBuilder<T> getMessageBuilder(Message<T> msg) {
        return MessageBuilder.fromMessage(msg);
    }
    public static String tokenToString(IMqttDeliveryToken token) throws MqttException {
        return  String.format("%s %s %s %s",token.isComplete(), token.getMessageId(),token.getResponse() ,token.getTopics(),token.getMessage());
    }


}

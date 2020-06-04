package com.github.zengfr.easymqtt4j.client.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.mqtt.inbound.AbstractMqttMessageDrivenChannelAdapter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by zengfr on 2020/5/13.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
@Component
public class MqttUtil {
    @Autowired
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static String getNowString() {
        return getNowString("yyyyMMddHHmmssSSS");
    }

    public static String getNowString(String format) {
        LocalDateTime time = LocalDateTime.now();

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern(format);
        return dtf2.format(time);

    }

    public static String buildClientId(String clientId, boolean useRandom) {
        if (useRandom)
            return clientId + "_" + getNowString();
        return clientId;
    }

    public static AbstractMqttMessageDrivenChannelAdapter getMessageDrivenChannelAdapter() {
        return getApplicationContext().getBean(AbstractMqttMessageDrivenChannelAdapter.class);
    }

    public static void addTopic(AbstractMqttMessageDrivenChannelAdapter adapter, boolean checkIfNotExists, String topic) {
        if (checkIfNotExists) {
            for (String topicName : adapter.getTopic()) {
                if (topicName.equals(topic)) {
                    return;
                }
            }
        }
        adapter.addTopic(topic);
    }

    public static void addTopic(AbstractMqttMessageDrivenChannelAdapter adapter, boolean checkIfNotExists, String topic, int qos) {
        if (checkIfNotExists) {
            for (String topicName : adapter.getTopic()) {
                if (topicName.equals(topic)) {
                    return;
                }
            }
        }
        adapter.addTopic(topic, qos);
    }
}

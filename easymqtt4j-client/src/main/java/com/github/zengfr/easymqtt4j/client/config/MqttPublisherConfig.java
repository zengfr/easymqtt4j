package com.github.zengfr.easymqtt4j.client.config;

import com.github.zengfr.easymqtt4j.client.geteway.MqttEventGateway;
import com.github.zengfr.easymqtt4j.client.interceptor.MqttChannelInterceptor;
import com.github.zengfr.easymqtt4j.client.listener.MqttEventListener;
import com.github.zengfr.easymqtt4j.client.util.MqttBuilderUtil;
import com.github.zengfr.easymqtt4j.client.util.MqttUtil;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.support.MqttMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
@Configuration
@IntegrationComponentScan
public class MqttPublisherConfig {

    @Value("${spring.mqtt.publisher.id:publisher}")
    private String clientId;
    @Value("${spring.mqtt.publisher.defaulttopic:}")
    private String defaultTopic;
    @Value("${spring.mqtt.publisher.defaultqos:0}")
    private int defaultQos;
    @Value("${spring.mqtt.publisher.completionTimeout:5000}")
    private int completionTimeout;
    @Value("${spring.mqtt.publisher.threads:2}")
    private int threads;
    @Value("${spring.mqtt.publisher.isuserndclientId:true}")
    private boolean isUseRndClientId;
    @Value("${spring.mqtt.publisher.ispayloadasbytes:false}")
    private boolean isPayloadAsBytes;
    @Autowired
    MqttPahoClientFactory mqttClientFactory;
    @Autowired
    MqttEventListener eventListener;
    @Autowired
    MqttEventGateway eventGateway;

    @Bean
    public ChannelInterceptor mqttOutboundChannelInterceptor() {

        return new MqttChannelInterceptor(clientId, eventGateway);
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return MqttBuilderUtil.buildMessageChannel(threads, mqttOutboundChannelInterceptor());
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutboundMessageHandler() {
        String id = MqttUtil.buildClientId(clientId, isUseRndClientId);
        MqttCallback callBackListener = MqttBuilderUtil.buildMqttCallback(clientId, eventGateway);
        MqttMessageConverter messageConverter = MqttBuilderUtil.buildMessageConverter(isPayloadAsBytes);

        MessageHandler messageHandler = MqttBuilderUtil.buildMessageHandler(
                mqttClientFactory, id, completionTimeout, messageConverter,
                defaultTopic, defaultQos, eventListener, callBackListener);
        return messageHandler;
    }
}

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
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.support.MqttMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
@Configuration
@IntegrationComponentScan
public class MqttSubscriberConfig {
    @Value("${spring.mqtt.subscriber.id:subscriber}")
    private String clientId;
    @Value("${spring.mqtt.subscriber.topics:}")
    private String[] topics;
    @Value("${spring.mqtt.subscriber.qos:1}")
    private int[] qos;
    @Value("${spring.mqtt.subscriber.defaultqos:1}")
    private int defaultQos;
    @Value("${spring.mqtt.subscriber.completionTimeout:5000}")
    private int completionTimeout;
    @Value("${spring.mqtt.subscriber.threads:2}")
    private int threads;
    @Value("${spring.mqtt.subscriber.isuserndclientId:true}")
    private boolean isUseRndClientId;
    @Value("${spring.mqtt.subscriber.ispayloadasbytes:false}")
    private boolean isPayloadAsBytes;
    @Autowired
    MqttPahoClientFactory mqttClientFactory;
    @Autowired
    MqttEventListener eventListener;
    @Autowired
    MqttEventGateway eventGateway;

    @Bean
    public ChannelInterceptor mqttInboundChannelInterceptor() {

        return new MqttChannelInterceptor(clientId, eventGateway);
    }

    @Bean
    public MessageChannel mqttInboundChannel() {
        return MqttBuilderUtil.buildMessageChannel(threads, mqttInboundChannelInterceptor());
    }

    @Bean
    public MessageProducer mqttInboundProducer() {
        String id = MqttUtil.buildClientId(clientId, isUseRndClientId);
        MqttCallback callBackListener = MqttBuilderUtil.buildMqttCallback(clientId, eventGateway);
        MqttMessageConverter messageConverter = MqttBuilderUtil.buildMessageConverter(isPayloadAsBytes);
        MessageProducer producer = MqttBuilderUtil.buildMessageProducer(
                mqttClientFactory, id, completionTimeout, mqttInboundChannel(), messageConverter, topics, defaultQos, qos, eventListener, callBackListener);

        return producer;
    }
}

package com.github.zengfr.easymqtt4j.client.util;


import com.github.zengfr.easymqtt4j.client.adapter.MqttPahoMessageDrivenChannelAdapterAdapter;
import com.github.zengfr.easymqtt4j.client.adapter.MqttPahoMessageHandlerAdapter;
import com.github.zengfr.easymqtt4j.client.geteway.MqttEventGateway;
import com.github.zengfr.easymqtt4j.client.listener.MqttCallbackListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
public class MqttBuilderUtil {
    public static MqttMessageConverter buildMessageConverter(boolean isPayloadAsBytes) {
        DefaultPahoMessageConverter messageConverter = new DefaultPahoMessageConverter();
        messageConverter.setPayloadAsBytes(isPayloadAsBytes);
        return messageConverter;
    }

    public static MqttCallback buildMqttCallback(String clientId, MqttEventGateway eventGateway) {

        MqttCallbackListener listener = new MqttCallbackListener(clientId, eventGateway);
        return listener;
    }

    public static MessageChannel buildMessageChannel(int threads, ChannelInterceptor channelInterceptor) {
        AbstractMessageChannel c;
        if (threads <= 0) {
            c = new DirectChannel();
        } else {
            Executor exector = Executors.newFixedThreadPool(threads);
            c = new ExecutorChannel(exector);
        }
        if (channelInterceptor != null) {
            c.addInterceptor(channelInterceptor);
        }
        return c;
    }

    public static MqttPahoClientFactory buildClientFactory(MqttConnectOptions connectOptions) {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(connectOptions);
        factory.setPersistence(new MemoryPersistence());

        return factory;
    }

    /**
     * 接收器 消息消费
     */
    public static MessageProducer buildMessageProducer(
            MqttPahoClientFactory clientFactory, String clientId, int completionTimeout,
            MessageChannel mqttInboundChannel, MqttMessageConverter messageConverter, String[] topics, int defaultQos, int[] qos, ApplicationEventPublisher eventPublisher, MqttCallback callback) {
        MqttPahoMessageDrivenChannelAdapter adapter = new
                MqttPahoMessageDrivenChannelAdapterAdapter(callback, clientId,
                clientFactory, topics);
        adapter.setCompletionTimeout(completionTimeout);
        adapter.setDisconnectCompletionTimeout(completionTimeout);
        adapter.setRecoveryInterval(800);
        adapter.setConverter(messageConverter);
        adapter.setQos(buildQos(qos, topics.length, defaultQos));
        adapter.setAutoStartup(true);
        adapter.setOutputChannel(mqttInboundChannel);
        adapter.setApplicationEventPublisher(eventPublisher);
        return adapter;
    }

    private static int[] buildQos(int[] qos, int len, int defaultQos) {
        int[] newqos = new int[len];
        for (int i = 0; i < newqos.length; i++) {
            if (qos != null && i < qos.length) newqos[i] = qos[i];
            else newqos[i] = defaultQos;
        }
        return newqos;
    }

    /**
     * 发送器 消息发送
     */
    public static MessageHandler buildMessageHandler(MqttPahoClientFactory clientFactory, String clientId,
                                                     int completionTimeout, MqttMessageConverter messageConverter, String defaultTopic, int defaultQos, ApplicationEventPublisher eventPublisher, MqttCallback callback) {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandlerAdapter(callback, clientId, clientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(defaultTopic);
        messageHandler.setCompletionTimeout(completionTimeout);
        messageHandler.setAsyncEvents(true);
        messageHandler.setDefaultQos(defaultQos);
        //messageHandler.setShouldTrack();
        messageHandler.setConverter(messageConverter);
        messageHandler.setApplicationEventPublisher(eventPublisher);
        return messageHandler;
    }
}

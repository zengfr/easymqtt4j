package com.github.zengfr.easymqtt4j.client.interceptor;

import com.github.zengfr.easymqtt4j.client.geteway.MqttEventGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
public class MqttChannelInterceptor implements ChannelInterceptor {
    private static Logger logger = LoggerFactory.getLogger(MqttChannelInterceptor.class);
    private String clientId;
    private MqttEventGateway eventGateway;

    public MqttChannelInterceptor(String clientId, MqttEventGateway eventGateway) {
        this.clientId = clientId;
        this.eventGateway = eventGateway;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        eventGateway.preSend(clientId, message, channel);
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        eventGateway.postSend(clientId, message, channel, sent);
    }

    @Override
    public void afterSendCompletion(
            Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {
        eventGateway.afterSendCompletion(clientId, message, channel, sent, ex);
    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        eventGateway.preReceive(clientId, channel);
        return true;
    }


    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        eventGateway.postReceive(clientId, message, channel);

        return message;
    }


    @Override
    public void afterReceiveCompletion(@Nullable Message<?> message, MessageChannel channel,
                                       @Nullable Exception ex) {
        eventGateway.afterReceiveCompletion(clientId, message, channel, ex);
    }
}

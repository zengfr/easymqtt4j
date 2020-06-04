package com.github.zengfr.easymqtt4j.client.listener;


import com.github.zengfr.easymqtt4j.client.geteway.MqttEventGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.integration.mqtt.event.MqttIntegrationEvent;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
public class MqttEventListener implements ApplicationEventPublisher {
    static Logger logger = LoggerFactory.getLogger(MqttEventListener.class);

    private MqttEventGateway eventGateway;

    public MqttEventListener(MqttEventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @EventListener({MqttIntegrationEvent.class})
    public void handleEvent(MqttIntegrationEvent event) {
        eventGateway.handleEvent(event);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

        eventGateway.handleEvent(event);
    }

    @Override
    public void publishEvent(Object event) {

    }
}

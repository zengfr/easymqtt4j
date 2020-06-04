package com.github.zengfr.easymqtt4j.client.adapter;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;

/**
 * Created by zengfr on 2020/5/13.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
public class MqttPahoMessageHandlerAdapter extends MqttPahoMessageHandler {
    private  MqttCallback callback;
    public MqttPahoMessageHandlerAdapter(MqttCallback callback, String url, String clientId, MqttPahoClientFactory clientFactory) {
        super(url, clientId, clientFactory);
        this.callback = callback;
    }

    public MqttPahoMessageHandlerAdapter(MqttCallback callback, String clientId, MqttPahoClientFactory clientFactory) {
        super(clientId, clientFactory);
        this.callback = callback;
    }

    public MqttPahoMessageHandlerAdapter(MqttCallback callback, String url, String clientId) {
        super(url, clientId);
        this.callback = callback;
    }

    @Override
    public void connectionLost(Throwable cause) {
        if (callback != null) {
            callback.connectionLost(cause);
        }
        super.connectionLost(cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        if (callback != null) {
            try {
                callback.messageArrived(topic, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.messageArrived(topic, message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        if (callback != null) {
            callback.deliveryComplete(token);
        }
        super.deliveryComplete(token);
    }
}

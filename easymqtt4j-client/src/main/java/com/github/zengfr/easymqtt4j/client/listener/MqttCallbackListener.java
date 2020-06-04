package com.github.zengfr.easymqtt4j.client.listener;

import com.github.zengfr.easymqtt4j.client.geteway.MqttEventGateway;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by zengfr on 2020/5/13.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
public class MqttCallbackListener implements MqttCallback {
    static Logger logger = LoggerFactory.getLogger(MqttCallbackListener.class);
    private String clientId;
    private MqttEventGateway eventGateway;

    public MqttCallbackListener(String clientId, MqttEventGateway eventGateway) {
        this.eventGateway = eventGateway;
        this.clientId=clientId;
    }

    @Override
    public void connectionLost(Throwable cause) {


        eventGateway.connectionLost(clientId,cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        eventGateway.messageArrived(clientId,topic, message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        eventGateway.deliveryComplete(clientId,token);
    }
}

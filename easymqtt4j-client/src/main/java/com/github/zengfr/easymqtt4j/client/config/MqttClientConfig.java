package com.github.zengfr.easymqtt4j.client.config;


import com.github.zengfr.easymqtt4j.client.geteway.MqttEventGateway;
import com.github.zengfr.easymqtt4j.client.listener.MqttEventListener;
import com.github.zengfr.easymqtt4j.client.util.MqttBuilderUtil;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
@Configuration
@IntegrationComponentScan
public class MqttClientConfig {
    @Value("${spring.mqtt.mqttVersion:0}")
    private int mqttVersion;

    @Value("${spring.mqtt.host.username}")
    private String username;

    @Value("${spring.mqtt.host.password}")
    private String password;

    @Value("${spring.mqtt.host.uris}")
    private String[] hostUris;

    @Value("${spring.mqtt.conn.cleansession:true}")
    private boolean cleanSession;

    @Value("${spring.mqtt.conn.keepaliveinterval:60}")
    private int keepAliveInterval;
    @Value("${spring.mqtt.conn.maxreconnectdelay:1000}")
    private int maxReconnectDelay;
    @Value("${spring.mqtt.conn.timeout:0}")
    private int connectionTimeout;
    @Value("${spring.mqtt.conn.maxInflight:99}")
    private int maxInflight;

    @Value("${spring.mqtt.conn.lastwilltopic:will}")
    private String lastwillTopic;

    @Value("${spring.mqtt.conn.lastwillmsg:offline}")
    private String lastwillMsg;

    @Value("${spring.mqtt.conn.lastwillqos:2}")
    private short lastwillQos;

    @Value("${spring.mqtt.conn.lastwillretain:true}")
    private boolean lastwillRetain;
    @Value("${spring.mqtt.conn.uselastwill:true}")
    private boolean isUseLastwill;
    @Value("${spring.mqtt.conn.usessl:false}")
    private boolean isUseSsl;
    @Autowired
    MqttEventGateway eventGateway;


    @Bean
    public MqttEventListener eventListener() {
        return new MqttEventListener(eventGateway);
    }
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        MqttPahoClientFactory factory = MqttBuilderUtil.buildClientFactory(getMqttConnectOptions());
        return factory;
    }

    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());
        mqttConnectOptions.setServerURIs(hostUris);
        mqttConnectOptions.setKeepAliveInterval(keepAliveInterval);
        mqttConnectOptions.setConnectionTimeout(connectionTimeout);
        mqttConnectOptions.setMaxReconnectDelay(maxReconnectDelay);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setMaxInflight(maxInflight);
        mqttConnectOptions.setCleanSession(cleanSession);
        mqttConnectOptions.setMqttVersion(mqttVersion);
        if (isUseSsl) {
            mqttConnectOptions.setSSLProperties(null);
        }
        if (isUseLastwill) {
            mqttConnectOptions.setWill(lastwillTopic, lastwillMsg.getBytes(), lastwillQos, lastwillRetain);
        }
        return mqttConnectOptions;
    }
}

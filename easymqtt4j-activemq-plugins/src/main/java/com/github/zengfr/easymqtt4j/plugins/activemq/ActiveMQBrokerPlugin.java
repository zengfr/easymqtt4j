package com.github.zengfr.easymqtt4j.plugins.activemq;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;

/**
 * Created by zengfr on 2020/6/1.
 */
public class ActiveMQBrokerPlugin implements BrokerPlugin {
    @Override
    public Broker installPlugin(Broker broker) throws Exception {
        return null;
    }
}

package com.zengfr.easymqtt4j.plugins;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.plugin.StatisticsBrokerPlugin;

/**
 * Created by zengfr on 2020/6/1.
 */
public class ActiveMQBrokerPlugin implements BrokerPlugin {
    @Override
    public Broker installPlugin(Broker broker) throws Exception {
        return null;
    }
}

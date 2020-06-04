package com.github.zengfr.easymqtt4j.plugins.activemq;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;

/**
 * Created by zengfr on 2020/6/1.
 */
public class ActiveMQBrokerFilter extends BrokerFilter   {
    public ActiveMQBrokerFilter(Broker next) {
        super(next);
    }
}

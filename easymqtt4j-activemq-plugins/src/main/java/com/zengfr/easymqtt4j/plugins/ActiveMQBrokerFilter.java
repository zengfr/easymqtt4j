package com.zengfr.easymqtt4j.plugins;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.command.Message;
import org.apache.activemq.plugin.StatisticsBrokerPlugin;

/**
 * Created by zengfr on 2020/6/1.
 */
public class ActiveMQBrokerFilter extends BrokerFilter   {
    public ActiveMQBrokerFilter(Broker next) {
        super(next);
    }
}

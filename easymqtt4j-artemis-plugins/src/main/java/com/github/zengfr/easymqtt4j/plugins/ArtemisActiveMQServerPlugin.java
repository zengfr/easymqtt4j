package com.github.zengfr.easymqtt4j.plugins;

import org.apache.activemq.artemis.api.core.*;
import org.apache.activemq.artemis.api.core.management.ActiveMQServerControl;
import org.apache.activemq.artemis.core.persistence.OperationContext;
import org.apache.activemq.artemis.core.postoffice.QueueBinding;
import org.apache.activemq.artemis.core.server.ServerSession;
import org.apache.activemq.artemis.core.server.plugin.ActiveMQServerPlugin;
import org.apache.activemq.artemis.core.server.plugin.impl.LoggingActiveMQServerPluginLogger;
import org.apache.activemq.artemis.core.transaction.Transaction;
import org.apache.activemq.artemis.spi.core.protocol.RemotingConnection;
import org.apache.activemq.artemis.spi.core.protocol.SessionCallback;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zengfr on 2020/5/15.
 */
public class ArtemisActiveMQServerPlugin implements
        ActiveMQServerPlugin, Serializable {
    public static final String LOG_ALL_EVENTS = "LOG_ALL_EVENTS";
    public static final String IS_ENABLED = "IsEnabled";

    private boolean logAll = false;
    private boolean isEnabled = true;
    @Override
    public void init(Map<String, String> properties) {
        logAll = Boolean.parseBoolean(properties.getOrDefault(LOG_ALL_EVENTS, "false"));
        isEnabled = Boolean.parseBoolean(properties.getOrDefault(IS_ENABLED, "true"));
    }

    @Override
    public void afterCreateConnection(RemotingConnection connection) throws ActiveMQException {
        ActiveMQServerControl activeMQServerControl;
      //  activeMQServerControl.addSecuritySettings();
       if(!isCheckOK()){
           connection.disconnect(false);
           //connection.
        }
        //Configuration config = new ConfigurationImpl();

        //config.registerBrokerPlugin(new AuthArtemisServerPlugin());
    }

    @Override
    public void beforeCreateSession(String name,
                                    String username,
                                    int minLargeMessageSize,
                                    RemotingConnection connection,
                                    boolean autoCommitSends,
                                    boolean autoCommitAcks,
                                    boolean preAcknowledge,
                                    boolean xa,
                                    String publicAddress,
                                    SessionCallback callback,
                                    boolean autoCreateQueues,
                                    OperationContext context,
                                    Map<SimpleString, RoutingType> prefixes)
            throws ActiveMQException {

    }

    @Override
    public void beforeCreateConsumer(long consumerID,
                                     QueueBinding queueBinding,
                                     SimpleString filterString,
                                     boolean browseOnly,
                                     boolean supportLargeMessage) throws ActiveMQException {

    }

    @Override
    public void beforeCreateQueue(QueueConfiguration queueConfig) throws ActiveMQException {

    }

    @Override
    public void beforeSend(ServerSession session,
                           Transaction tx,
                           Message message,
                           boolean direct,
                           boolean noAutoCreateQueue) throws ActiveMQException {


    }
    private  static  boolean isCheckOK(){
        LoggingActiveMQServerPluginLogger.LOGGER.info("");
        return  true;
    }
}

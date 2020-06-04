package com.github.zengfr.easymqtt4j.test.case1;


import com.github.zengfr.easymqtt4j.client.geteway.MqttPublisherGateway;
import com.github.zengfr.easymqtt4j.client.util.MqttUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
@Configuration
@EnableScheduling
@EnableAsync
public class MqttScheduleTask {
    private static Logger logger = LoggerFactory.getLogger(MqttScheduleTask.class);
    @Autowired
    private MqttPublisherGateway publisherGateway;

    @Scheduled(fixedRate = 1000*10,initialDelay = 1000*5)
    public void sendMqtt1() {
        logger.info("发送开始");
        String d= MqttUtil.getNowString();
        publisherGateway.publish("testtopic/0", 0, "hello 10 " + d);
        logger.info("发送结束");
    }

    @Scheduled(fixedRate = 1000*30,initialDelay = 1000*5)
    public void sendMqtt2() throws InterruptedException {
        int count = 11;
        for (int i = 0; i < count; i++) {
            logger.info("发送开始");
            String d= MqttUtil.getNowString();
            publisherGateway.publish("topic/"+i, i%3, "hello 00 " + d);
            publisherGateway.publish("testtopic/"+i, i%3, "hello 10 " + d);
            logger.info("发送结束");
        }
    }
}

package com.github.zengfr.easymqtt4j.test.case1;

import com.github.zengfr.easymqtt4j.test.MqttTestApplication;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by zengfr on 2020/5/12.
 * https://gitee.com/zengfr/easymqtt4j
 * https://github.com/zengfr/easymqtt4j
 */
@SpringBootTest(classes = {MqttTestApplication.class})
public class MqttScheduleTaskTest {
    static Logger logger = LoggerFactory.getLogger(MqttScheduleTaskTest.class);
    @Autowired
    private MqttScheduleTask task;

    @Test
    public void sendMqtt1() {

        task.sendMqtt1();
    }

    @Test
    public void sendMqtt2() throws InterruptedException {

        task.sendMqtt2();

    }
}

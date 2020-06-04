# easymqtt4j
easymqtt4j , netty +mqtt +subscriber+ publisher +broker+cluster server for java 

Java使用easymqtt4j快速开发工业级mqtt企业级应用

easymqtt4j , netty +mqtt +subscriber+ publisher +broker+cluster server for java
### easymqtt4j特点：
- 1、spring integration 集成模式，自由灵活
- 2、完全支持mqtt 3.1、3.1.1国际标准协议，支持tcp\websocket等等,可配置。
- 3、客户端完全支持接入主流broker服务如：Eclipse Paho，Mosquitto，JBoss A-MQ 6.1, Apache ActiveMQ 5.10-SNAPSHOT，Apache Camel 2.13.0，HiveMQ，EMQ,mosquitto,moquette,JMQTT,mqttwk等等。
- 4、发布、订阅接口简单&统一Gateway。
- 5、完全支持event事件EventGateway，灵活自由控制。
- 6、支持handleEvent、connectionLost、 messageArrived、deliveryComplete。
- 7、支持preSend、postSend、afterSendCompletion。
- 8、支持preReceive、postReceive、afterReceiveCompletion。
### easymqtt4j使用方法&步骤：
- 1、引用jar 
- 2、实现 MqttSubscriberGateway消息队列订阅 接口 
- 3、实现 MqttEventGateway 事件 接口 
- 4、MqttPublisherGateway消息发送 接口 （ 使用请参考 MqttScheduleTask 消息定时发送）
### open project home site
- 项目开源地址1： https://github.com/zengfr/easymqtt4j
- 项目开源地址2： https://gitee.com/zengfr/easymqtt4j
### project test and demo 
- 测试代码 ：   https://github.com/zengfr/easymqtt4j/tree/master/easymqtt4j-test/src/main/java/com/zengfr/easymqtt4j/test

- 详细见 ：https://my.oschina.net/zengfr/blog/4281902
### other相关开源项目推荐
- https://github.com/zengfr/easymodbus4j

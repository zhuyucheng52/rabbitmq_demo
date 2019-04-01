package com.example.topic.mq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yucheng
 * @Description
 * @create 2019-03-30 17:38
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.example.mq")
public class RabbitMQProperties {
    private String username;
    private String password;
    private String virtualHost;
    private String host;
    private Integer port;


    private String exchangeName;
    private String exchangeType;

    private String queueName;

    private String bindingKey;
}

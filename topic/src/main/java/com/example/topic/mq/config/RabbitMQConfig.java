package com.example.topic.mq.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yucheng
 * @Description
 * @create 2019-03-30 17:33
 */
@Configuration
public class RabbitMQConfig implements DisposableBean {
    @Autowired
    private RabbitMQProperties properties;

    @Bean
    public ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost(properties.getVirtualHost());
        factory.setUsername(properties.getUsername());
        factory.setPassword(properties.getPassword());
        factory.setPort(properties.getPort());
        return factory;
    }

    @Bean
    public Connection getConnection() throws IOException, TimeoutException {
        return getConnectionFactory().newConnection();
    }

    @Bean
    public Channel getChannel() throws IOException, TimeoutException {
        return getConnection().createChannel();
    }

    @PostConstruct
    public void buildExchange() throws IOException, TimeoutException {
        Channel channel = getChannel();
        channel.exchangeDeclare(properties.getExchangeName(), properties.getExchangeType(), true);
    }

    @PostConstruct
    public String buildQueue() throws IOException, TimeoutException {
        Channel channel = getChannel();
        return channel.queueDeclare(properties.getQueueName(), true, false, false, null).getQueue();
    }

    @PostConstruct
    public void buildBind() throws IOException, TimeoutException {
        Channel channel = getChannel();
        channel.queueBind(properties.getQueueName(), properties.getExchangeName(), properties.getBindingKey());
    }

    @Override
    public void destroy() throws Exception {
        getChannel().close();
        getConnection().close();
    }
}

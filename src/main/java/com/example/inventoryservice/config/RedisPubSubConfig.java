package com.example.inventoryservice.config;

import com.example.inventoryservice.alert.AlertPublisher;
import com.example.inventoryservice.alert.AlertSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisPubSubConfig {

    @Bean
    public RedisMessageListenerContainer listenerContainer(
            RedisConnectionFactory factory, AlertSubscriber subscriber) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(subscriber, new ChannelTopic(AlertPublisher.CHANNEL));
        return container;
    }
}
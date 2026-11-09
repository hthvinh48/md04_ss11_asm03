package com.example.inventoryservice.alert;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertPublisher {

    public static final String CHANNEL = "pharmacy-alerts";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public void publish(AlertMessage alert) {
        String json = objectMapper.writeValueAsString(alert);
        Long receivers = redisTemplate.convertAndSend(CHANNEL, json);
        log.info("Đã publish tới {} subscriber: {}", receivers, json);
    }
}
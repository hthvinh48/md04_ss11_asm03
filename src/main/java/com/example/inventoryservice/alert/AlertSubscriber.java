package com.example.inventoryservice.alert;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String json = new String(message.getBody(), StandardCharsets.UTF_8);
        AlertMessage alert = objectMapper.readValue(json, AlertMessage.class);
        System.out.println(">>> [DASHBOARD] " + alert.getType() + ": " + alert.getMessage());
    }
}
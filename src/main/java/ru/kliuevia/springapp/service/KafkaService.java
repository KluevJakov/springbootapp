package ru.kliuevia.springapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {

    @Value("${smsapi.topic}")
    private String TOPIC_NAME;

    private final KafkaTemplate<Void, String> kafkaTemplate;

    public void publish(String value) {
        kafkaTemplate.send(TOPIC_NAME, value);
    }
}

package ru.kliuevia.springapp.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConditionalOnProperty(value = "spring.scheduling.enabled", havingValue = "true")
@Configuration
@EnableScheduling
public class ScheduledConfig {
}

package ru.kliuevia.springapp.scheduledtasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ScheduledTask {

    @Scheduled(cron = "*/2 * * * * *")
    private void task() {
        log.info("Запуск задания по расписанию");
    }
}

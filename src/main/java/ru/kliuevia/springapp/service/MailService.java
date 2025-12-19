package ru.kliuevia.springapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    @Value("${spring.mail.username}")
    private String FROM;

    private final JavaMailSender mailSender;

    public void send(String subject, String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject(subject);
        message.setTo(to);
        message.setFrom(FROM);
        message.setText(text);

        mailSender.send(message);
    }
}

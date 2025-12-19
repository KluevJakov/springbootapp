package ru.kliuevia.springapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kliuevia.springapp.entity.dto.request.MailSendDto;
import ru.kliuevia.springapp.service.MailService;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping
    public void send(@RequestBody MailSendDto request) {
        mailService.send(request.getSubject(), request.getTo(), request.getText());
    }
}

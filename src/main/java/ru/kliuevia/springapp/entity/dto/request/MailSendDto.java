package ru.kliuevia.springapp.entity.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailSendDto {
    private String subject;
    private String to;
    private String text;
}

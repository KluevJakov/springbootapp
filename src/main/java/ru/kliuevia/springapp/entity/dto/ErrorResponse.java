package ru.kliuevia.springapp.entity.dto;

import lombok.*;

@Data
@Builder
public class ErrorResponse {
    private String message;
}

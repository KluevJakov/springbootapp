package ru.kliuevia.springapp.controller.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kliuevia.springapp.entity.dto.ErrorResponse;
import ru.kliuevia.springapp.exceptions.NotFoundException;

import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        Optional<ObjectError> optErrorMsg = e.getBindingResult().getAllErrors().stream().findFirst();
        String errorMsg = "Непредвиденная ошибка";

        if (optErrorMsg.isPresent()) {
            errorMsg = optErrorMsg.get().getDefaultMessage();
        }

        log.error(e.getMessage());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .message(errorMsg)
                        .build());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
    }
}

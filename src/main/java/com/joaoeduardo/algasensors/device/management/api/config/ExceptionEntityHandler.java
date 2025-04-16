package com.joaoeduardo.algasensors.device.management.api.config;

import com.joaoeduardo.algasensors.device.management.domain.exceptions.*;
import jakarta.servlet.http.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.stream.*;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(SensorNotFoundException.class)
    public ResponseEntity<StandardError> handleEventNotFoundException(SensorNotFoundException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError(Instant.now(), status.value(), "Event not found!",
                exception.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

//
//    @ExceptionHandler(UserAlreadySubscribedException.class)
//    public ResponseEntity<StandardError> handleUserAlreadySubscribedException(UserAlreadySubscribedException exception, HttpServletRequest request){
//        HttpStatus status = HttpStatus.CONFLICT;
//        StandardError error = new StandardError(Instant.now(), status.value(), "Subscription error!",
//                exception.getMessage(), request.getRequestURI());
//
//        return ResponseEntity.status(status).body(error);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidationMethodArgumentNotValidException(
            MethodArgumentNotValidException exception, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Validation error",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

}

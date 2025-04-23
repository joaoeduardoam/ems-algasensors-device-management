package com.joaoeduardo.algasensors.device.management.api.config;

import com.joaoeduardo.algasensors.device.management.domain.exceptions.*;
import jakarta.servlet.http.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.time.*;
import java.util.stream.*;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler({
            SocketTimeoutException.class,
            ConnectException.class,
            ClosedChannelException.class
    })
    public ProblemDetail handle(IOException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.GATEWAY_TIMEOUT);

        problemDetail.setTitle("IOException - Gateway timeout");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("/errors/gateway-timeout"));

        return problemDetail;
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ProblemDetail handle(ResourceAccessException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.GATEWAY_TIMEOUT);

        problemDetail.setTitle("ResourceAccessException - Gateway timeout");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("/errors/gateway-timeout"));

        return problemDetail;
    }

    @ExceptionHandler(SensorMonitoringClientBadGatewayException.class)
    public ProblemDetail handle(SensorMonitoringClientBadGatewayException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);

        problemDetail.setTitle("Bad gateway");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("/errors/bad-gateway"));

        return problemDetail;
    }

    @ExceptionHandler(SensorNotFoundException.class)
    public ResponseEntity<StandardError> handleEventNotFoundException(SensorNotFoundException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError(Instant.now(), status.value(), "Sensor not found!",
                exception.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

//    @ExceptionHandler(SensorMonitoringClientBadGatewayException.class)
//    public ResponseEntity<StandardError> handleSensorMonitoringClientBadGatewayException(SensorMonitoringClientBadGatewayException exception, HttpServletRequest request){
//        HttpStatus status = HttpStatus.BAD_GATEWAY;
//        StandardError error = new StandardError(Instant.now(), status.value(), "RestClient Error",
//                exception.getMessage(), request.getRequestURI());
//
//        return ResponseEntity.status(status).body(error);
//    }

    @ExceptionHandler(SensorMonitoringClientEnableException.class)
    public ResponseEntity<StandardError> handleSensorMonitoringClientEnableException(SensorMonitoringClientEnableException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_GATEWAY;
        StandardError error = new StandardError(Instant.now(), status.value(), "RestClient Error: not was possible enable/disable the sensor. ",
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

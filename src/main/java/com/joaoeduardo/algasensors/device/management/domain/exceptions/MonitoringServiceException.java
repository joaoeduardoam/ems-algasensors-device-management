package com.joaoeduardo.algasensors.device.management.domain.exceptions;

public class MonitoringServiceException extends RuntimeException{

    public MonitoringServiceException(String message, Exception ex){
        super(message);
    }
}

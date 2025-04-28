package com.joaoeduardo.algasensors.device.management.domain.exceptions;

public class SensorMonitoringClientEnableException extends RuntimeException{

    public SensorMonitoringClientEnableException(String message, Exception ex){
        super(message);
    }

    public SensorMonitoringClientEnableException(){
        super();
    }
}

package com.joaoeduardo.algasensors.device.management.domain.exceptions;

public class SensorMonitoringClientBadGatewayException extends RuntimeException{

    public SensorMonitoringClientBadGatewayException(String message, Exception ex){
        super(message);
    }

    public SensorMonitoringClientBadGatewayException(){
        super();
    }
}

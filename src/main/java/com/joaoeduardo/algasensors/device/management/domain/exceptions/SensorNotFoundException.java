package com.joaoeduardo.algasensors.device.management.domain.exceptions;

public class SensorNotFoundException extends RuntimeException{

    public SensorNotFoundException(String message){
        super(message);
    }
}

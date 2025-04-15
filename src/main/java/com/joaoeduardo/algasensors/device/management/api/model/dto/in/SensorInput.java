package com.joaoeduardo.algasensors.device.management.api.model.dto.in;


public record SensorInput (
        String name,
        String ip,
        String location,
        String protocol,
        String model

){}

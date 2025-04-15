package com.joaoeduardo.algasensors.device.management.api.model.dto.out;


import io.hypersistence.tsid.*;
import lombok.*;

@Builder
public record SensorOutput (
        TSID id,
        String name,
        String ip,
        String location,
        String protocol,
        String model

){}

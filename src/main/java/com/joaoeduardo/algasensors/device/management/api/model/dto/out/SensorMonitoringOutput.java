package com.joaoeduardo.algasensors.device.management.api.model.dto.out;


import io.hypersistence.tsid.*;
import lombok.*;

import java.time.*;

@Builder
public record SensorMonitoringOutput(
        TSID id,
        Double lastTemperature,
        OffsetDateTime updatedAt,
        Boolean enabled

){}

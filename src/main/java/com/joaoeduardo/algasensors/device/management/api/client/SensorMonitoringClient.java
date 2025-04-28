package com.joaoeduardo.algasensors.device.management.api.client;

import com.joaoeduardo.algasensors.device.management.api.model.dto.out.*;
import io.hypersistence.tsid.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.*;

@HttpExchange("/api/sensors/{sensorId}/monitoring")
public interface SensorMonitoringClient {

    @PutExchange("/enable")
    void enableMonitoring(@PathVariable TSID sensorId);

    @DeleteExchange("/enable")
    void disableMonitoring(@PathVariable TSID sensorId);

    @GetExchange
    SensorMonitoringOutput getDetail(@PathVariable TSID sensorId);
}

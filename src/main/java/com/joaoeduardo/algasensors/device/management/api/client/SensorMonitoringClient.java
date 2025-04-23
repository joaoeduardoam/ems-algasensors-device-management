package com.joaoeduardo.algasensors.device.management.api.client;

import com.joaoeduardo.algasensors.device.management.api.model.dto.out.*;
import io.hypersistence.tsid.*;

public interface SensorMonitoringClient {
    void enableMonitoring(TSID sensorId);
    void disableMonitoring(TSID sensorId);
    SensorMonitoringOutput getDetail(TSID sensorId);
}

package com.joaoeduardo.algasensors.device.management.api.client.impl;

import com.joaoeduardo.algasensors.device.management.api.client.*;
import com.joaoeduardo.algasensors.device.management.domain.exceptions.*;
import io.hypersistence.tsid.*;
import org.slf4j.*;
import org.springframework.stereotype.*;



import com.joaoeduardo.algasensors.device.management.api.client.SensorMonitoringClient;
import io.hypersistence.tsid.TSID;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {

    private final RestClient restClient;
    private static final Logger log = LoggerFactory.getLogger(SensorMonitoringClientImpl.class);


    public SensorMonitoringClientImpl(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8082")
                .build();
    }

    @Override
    public void enableMonitoring(TSID sensorId) {
        try {
            restClient.put()
                    .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception ex) {
            log.error("Failed to enable monitoring for sensor: {}", sensorId, ex);
            throw new MonitoringServiceException("Unable to connect to monitoring service", ex);
        }
    }

    @Override
    public void disableMonitoring(TSID sensorId) {
        try {
            restClient.delete()
                    .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception ex) {
            log.error("Failed to disable monitoring for sensor: {}", sensorId, ex);
            throw new MonitoringServiceException("Unable to connect to monitoring service", ex);
        }
    }
}

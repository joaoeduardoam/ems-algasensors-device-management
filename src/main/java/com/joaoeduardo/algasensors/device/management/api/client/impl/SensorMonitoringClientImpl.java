package com.joaoeduardo.algasensors.device.management.api.client.impl;

import com.joaoeduardo.algasensors.device.management.api.client.*;
import com.joaoeduardo.algasensors.device.management.api.model.dto.out.*;
import org.slf4j.*;


import io.hypersistence.tsid.TSID;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.*;

@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {

    private final RestClient restClient;
    private static final Logger log = LoggerFactory.getLogger(SensorMonitoringClientImpl.class);


    public SensorMonitoringClientImpl(RestClientFactory factory) {
        this.restClient = factory.temperatureMonitoringRestClient();
    }



    @Override
    public void enableMonitoring(TSID sensorId) {
        restClient.put()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();

    }

    @Override
    public void disableMonitoring(TSID sensorId) {
        restClient.delete()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public SensorMonitoringOutput getDetail(TSID sensorId) {
        return restClient.get()
                .uri("/api/sensors/{sensorId}/monitoring", sensorId)
                .retrieve()
                .body(SensorMonitoringOutput.class);
    }
}

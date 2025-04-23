package com.joaoeduardo.algasensors.device.management.api.client.impl;

import com.joaoeduardo.algasensors.device.management.domain.exceptions.*;
import org.slf4j.*;


import com.joaoeduardo.algasensors.device.management.api.client.SensorMonitoringClient;
import io.hypersistence.tsid.TSID;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.*;

@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {

    private final RestClient restClient;
    private static final Logger log = LoggerFactory.getLogger(SensorMonitoringClientImpl.class);


    public SensorMonitoringClientImpl(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8082")
                .requestFactory(generateClientHttpRequestFactory())
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    throw new SensorMonitoringClientBadGatewayException();
                })
                .build();
    }

    private ClientHttpRequestFactory generateClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setReadTimeout(Duration.ofSeconds(5));
        factory.setConnectTimeout(Duration.ofSeconds(3));

        return factory;
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
}

package com.joaoeduardo.algasensors.device.management.api.controller;

import com.joaoeduardo.algasensors.device.management.api.client.*;
import com.joaoeduardo.algasensors.device.management.api.client.impl.*;
import com.joaoeduardo.algasensors.device.management.api.config.mapper.*;
import com.joaoeduardo.algasensors.device.management.api.model.dto.in.*;
import com.joaoeduardo.algasensors.device.management.api.model.dto.out.*;
import com.joaoeduardo.algasensors.device.management.common.IdGenerator;
import com.joaoeduardo.algasensors.device.management.domain.exceptions.*;
import com.joaoeduardo.algasensors.device.management.domain.model.*;
import com.joaoeduardo.algasensors.device.management.domain.repository.*;
import io.hypersistence.tsid.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.data.web.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorRepository sensorRepository;
    private final SensorMonitoringClient sensorMonitoringClient;

    private final IMapper mapper;


    @GetMapping
    public Page<SensorOutput> getAll(@PageableDefault Pageable pageable) {
        Page<Sensor> sensors = sensorRepository.findAll(pageable);
        return sensors.map(mapper::toSensorOutput);
    }


    @GetMapping("{sensorId}")
    public SensorOutput getOne(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return mapper.toSensorOutput(sensor);
    }

    @GetMapping("{sensorId}/detail")
    public SensorDetailOutput getOneWithDetail(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        SensorMonitoringOutput sensorMonitoringOutput = sensorMonitoringClient.getDetail(sensorId);

        return SensorDetailOutput.builder()
                .sensorOutput(mapper.toSensorOutput(sensor))
                .sensorMonitoringOutput(sensorMonitoringOutput)
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorOutput create(@RequestBody SensorInput input) {

        Sensor sensor = Sensor.builder()
                .id(new SensorId(IdGenerator.generateTSID()))
                .name(input.name())
                .ip(input.ip())
                .location(input.location())
                .protocol(input.protocol())
                .model(input.model())
                .enabled(false)
                .build();

        sensor = sensorRepository.saveAndFlush(sensor);

        return mapper.toSensorOutput(sensor);
    }


    @PutMapping("{sensorId}")
    @ResponseStatus(HttpStatus.OK)
    public SensorOutput updateSensor(@PathVariable TSID sensorId, @RequestBody SensorInput input) {

        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new SensorNotFoundException("Sensor not found with ID: " + sensorId));


        Sensor updatedSensor = mapper.toSensor(input);

        updatedSensor.setId(sensor.getId());

        return mapper.toSensorOutput(
                sensorRepository.save(updatedSensor));
    }

    @PutMapping("{sensorId}/enable")
    @ResponseStatus(HttpStatus.OK)
    public SensorOutput enableSensor(@PathVariable TSID sensorId) {

        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new SensorNotFoundException("Sensor not found with ID: " + sensorId));

        sensor.setEnabled(true);

        sensorMonitoringClient.enableMonitoring(sensorId);

        return mapper.toSensorOutput(
                sensorRepository.save(sensor));
    }

    @DeleteMapping("{sensorId}/enable")
    @ResponseStatus(HttpStatus.OK)
    public SensorOutput disableSensor(@PathVariable TSID sensorId) {

        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new SensorNotFoundException("Sensor not found with ID: " + sensorId));

        sensor.setEnabled(false);

        sensorMonitoringClient.disableMonitoring(sensorId);

        return mapper.toSensorOutput(
                sensorRepository.save(sensor));
    }

    @DeleteMapping("{sensorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSensor(@PathVariable TSID sensorId) {

        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new SensorNotFoundException("Sensor not found with ID: " + sensorId));

        sensorMonitoringClient.disableMonitoring(sensorId);

        sensorRepository.delete(sensor);

    }

}

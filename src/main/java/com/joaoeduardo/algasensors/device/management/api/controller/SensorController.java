package com.joaoeduardo.algasensors.device.management.api.controller;

import com.joaoeduardo.algasensors.device.management.api.config.mapper.*;
import com.joaoeduardo.algasensors.device.management.api.model.dto.in.*;
import com.joaoeduardo.algasensors.device.management.api.model.dto.out.*;
import com.joaoeduardo.algasensors.device.management.common.IdGenerator;
import com.joaoeduardo.algasensors.device.management.domain.model.*;
import com.joaoeduardo.algasensors.device.management.domain.repository.*;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorRepository sensorRepository;
    private final IMapper mapper;

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

}

package com.joaoeduardo.algasensors.device.management.api.config.mapper;


import com.joaoeduardo.algasensors.device.management.api.model.dto.out.*;
import com.joaoeduardo.algasensors.device.management.domain.model.*;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.*;

@Mapper(componentModel = SPRING)
public interface IMapper {


    @Mapping(source = "id.value", target = "id")
    SensorOutput toSensorOutput(Sensor sensor);

}

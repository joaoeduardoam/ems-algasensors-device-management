package com.joaoeduardo.algasensors.device.management.domain.repository;

import com.joaoeduardo.algasensors.device.management.domain.model.*;
import org.springframework.data.jpa.repository.*;


public interface SensorRepository extends JpaRepository<Sensor, SensorId> {
}

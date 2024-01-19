package com.kyura.message.repository;

import com.kyura.message.models.Hospitals;
import com.kyura.message.models.Schedules;
import com.kyura.message.models.SchedulesDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedules, Long> {
    void deleteAllByHospitals(Hospitals hospitals);
}

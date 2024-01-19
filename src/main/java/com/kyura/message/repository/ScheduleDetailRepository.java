package com.kyura.message.repository;

import com.kyura.message.models.Schedules;
import com.kyura.message.models.SchedulesDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleDetailRepository extends JpaRepository<SchedulesDetail, Long> {
    void deleteAllBySchedules(Schedules schedules);
}

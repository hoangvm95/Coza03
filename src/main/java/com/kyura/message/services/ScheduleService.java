package com.kyura.message.services;

import com.kyura.message.models.Schedules;

import java.time.LocalTime;

public interface ScheduleService {
    void handleSchedule(long id);
}

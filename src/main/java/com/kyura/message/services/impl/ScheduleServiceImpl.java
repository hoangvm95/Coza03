package com.kyura.message.services.impl;

import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.models.Schedules;
import com.kyura.message.models.SchedulesDetail;
import com.kyura.message.repository.ScheduleDetailRepository;
import com.kyura.message.repository.ScheduleRepository;
import com.kyura.message.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleDetailRepository scheduleDetailRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Override
    public void handleSchedule(long id) {
        Optional<Schedules> schedulesOptional = scheduleRepository.findById(id);
        Schedules schedules = schedulesOptional.get();
        String[] days = schedules.getSchedules_date().split(", ");
        Set<SchedulesDetail> generatedDetails = new HashSet<>();

        for (String day : days) {
            LocalTime startTime = schedules.getStart_time();
            LocalTime endTime = schedules.getEnd_time();

            while (startTime.isBefore(endTime)) {
                SchedulesDetail detail = new SchedulesDetail();
                detail.setSchedules(schedules);
                detail.setSchedules_date(day);
                detail.setStart_time(startTime);
                detail.setEnd_time(startTime.plusHours(1));
                detail.setStatus(ACTIVE_STATUS.INACTIVE);
                // Save the generated SchedulesDetail to the database
                scheduleDetailRepository.save(detail);
                generatedDetails.add(detail);
                startTime = startTime.plusHours(1);
            }
        }
        schedules.setListSchedulesDetail(generatedDetails);
        scheduleRepository.save(schedules);
    }
}

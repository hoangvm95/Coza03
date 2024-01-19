package com.kyura.message.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHospitalRequest {
    private Long id;
    private String name;
    private String desciption;
    private String address;
    private String username;
    private String schedules_date;
    private LocalTime start_time;
    private LocalTime end_time;
}

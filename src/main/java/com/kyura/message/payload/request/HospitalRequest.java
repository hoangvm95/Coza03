package com.kyura.message.payload.request;

import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.common.ROLE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalRequest {
    private String name;
    private String desciption;
    private String address;
    private String username;
    private String email;
    private String phone;
    private Set<ROLE> role;
    private String password = "sfdgsdfgsg444";
    private String schedules_date;
    private LocalTime start_time;
    private LocalTime end_time;
}

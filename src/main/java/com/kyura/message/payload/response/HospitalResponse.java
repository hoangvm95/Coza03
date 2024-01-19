package com.kyura.message.payload.response;


import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.models.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalResponse {
    private Long id;
    private String name;
    private String desciption;
    private String address;
    private ACTIVE_STATUS status;
    private String username;
    private Schedules schedules;
    private Set<Specialists> specialists = new HashSet<>();
}

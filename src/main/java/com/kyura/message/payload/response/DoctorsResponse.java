package com.kyura.message.payload.response;

import com.kyura.message.models.Hospitals;
import com.kyura.message.models.Schedules;
import com.kyura.message.models.Specialists;
import com.kyura.message.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class DoctorsResponse {

    private Long id;

    private Set<Specialists> specialityId;

    private String hospitalsName;

    private Long UserId;

    private Date schedules;
//    private String emailDoctor;
//    private String nameDoctor;
    private String fullNameDoctor;
    private String address;
    private String gender;
    private Date date_of_birth;
    private String phoneNumber;
}
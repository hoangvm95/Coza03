package com.kyura.message.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorBySpecialtyReponse {
    private Long id;
    private String fullName;
    private String phone;
    private String nameHospital;
    private String description;

}

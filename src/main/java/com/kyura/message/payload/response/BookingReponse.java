package com.kyura.message.payload.response;

import com.kyura.message.common.ACTIVE_STATUS;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Setter
@Getter
public class BookingReponse {

    private Long id;
    private String code;
    private String fullNameUser;
    private String phoneUser;
    private String nameHospital;
    private String addressHospital;
    private String namePackageClinic;
    private BigDecimal pricePackageClinic;
    private String fullNameDoctor;
    private Date booking_date;
    private String booking_time;
    private String description;
    private ACTIVE_STATUS status;
    //private ACTIVE_STATUS statusTransaction;
}

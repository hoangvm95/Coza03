package com.kyura.message.services;

import com.kyura.message.models.Doctors;
import com.kyura.message.payload.request.DoctorsRequest;

import com.kyura.message.payload.response.DoctorBySpecialtyReponse;

import com.kyura.message.payload.request.UpdateDoctorsRequest;

import com.kyura.message.payload.response.DoctorsResponse;

import java.util.List;

public interface DoctorsService {
    List<DoctorsResponse> findAll();
    Long addDoctor(DoctorsRequest doctorsRequest);
    Long updateDoctor(UpdateDoctorsRequest updateDoctorsRequest);


    public List<DoctorBySpecialtyReponse> searchDoctorsBySpecialtyId(Long specialityId);


    Doctors findByid(Long id);

}

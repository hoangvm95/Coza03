package com.kyura.message.services;

import com.kyura.message.models.Hospitals;
import com.kyura.message.payload.request.HospitalRequest;
import com.kyura.message.payload.request.UpdateHospitalRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HospitalService {
    List<Hospitals> findAll();
    Hospitals findById(long id);
    ResponseEntity<?> addHospital(HospitalRequest hospitalRequest);
    ResponseEntity<?> updateHospital(UpdateHospitalRequest hospitalRequest);
}

package com.kyura.message.services;

import com.kyura.message.models.Specialists;
import com.kyura.message.payload.response.SpecialistsResponse;

import java.util.List;

public interface SpecialistsService {
    List<SpecialistsResponse> getAllSpecialists();
    Specialists findById(Long Id);
}

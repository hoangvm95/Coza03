package com.kyura.message.services.impl;

import com.kyura.message.models.Specialists;
import com.kyura.message.payload.response.SpecialistsResponse;
import com.kyura.message.repository.SpecialistRepository;
import com.kyura.message.services.SpecialistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecialistsServiceImpl implements SpecialistsService {
    @Autowired
    private SpecialistRepository specialistRepository;

    @Override
    public List<SpecialistsResponse> getAllSpecialists() {
    List<SpecialistsResponse> specialistsResponseList = new ArrayList<>();
    List<Specialists> specialistsList = specialistRepository.findAll();
    for (Specialists item : specialistsList){
        SpecialistsResponse response = new SpecialistsResponse();

        response.setId(item.getId());
        response.setName(item.getName());
        response.setDescription(item.getDescription());
        response.setStatus(item.getStatus());
        response.setThumbnail(item.getThumbnail());

        response.setListPackageClinics(item.getListPackageClinics());

        specialistsResponseList.add(response);
    }
        return specialistsResponseList;
    }

    @Override
    public Specialists findById(Long Id) {
    Specialists specialists = specialistRepository.findById(Id);
        return specialists;
    }
}

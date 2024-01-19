package com.kyura.message.controllers;

import com.kyura.message.models.Hospitals;
import com.kyura.message.payload.request.HospitalRequest;
import com.kyura.message.payload.request.UpdateHospitalRequest;
import com.kyura.message.payload.response.BaseResponse;
import com.kyura.message.services.HospitalService;
import com.kyura.message.services.impl.HospitalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/hospital")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;
    @GetMapping("")
    public ResponseEntity<?> getAllSpecialists(){
        BaseResponse response = new BaseResponse();
        response.setData(hospitalService.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getHospitalById(@PathVariable(name = "id") long id){
        BaseResponse response = new BaseResponse();
        response.setData(hospitalService.findById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addHospital(@RequestBody HospitalRequest hospitalRequest){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(hospitalService.addHospital(hospitalRequest));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateHospital(@RequestBody UpdateHospitalRequest hospitalRequest){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(hospitalService.updateHospital(hospitalRequest));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}

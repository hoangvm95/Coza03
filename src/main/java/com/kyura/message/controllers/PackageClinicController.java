package com.kyura.message.controllers;

import com.kyura.message.models.PackageClinic;
import com.kyura.message.payload.request.PackageRequest;
import com.kyura.message.payload.request.UpdatePackageClinicRequest;
import com.kyura.message.payload.response.BaseResponse;
import com.kyura.message.services.PackageClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packageClinic")
public class PackageClinicController {
    @Autowired
    private PackageClinicService packageClinicService;
    @GetMapping("")
    public List<PackageClinic> getAllPackage(){
        return packageClinicService.getAllPackage();
    }
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addPackage(@RequestBody PackageRequest packageRequest){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(packageClinicService.addPackage(packageRequest));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPackageById(@PathVariable long id){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(packageClinicService.findByid(id));
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }
    @PutMapping("/updatePackageClinic")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updatePackageClinic(@RequestBody UpdatePackageClinicRequest updatePackageClinicRequest){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(packageClinicService.updatePackageClinic(updatePackageClinicRequest));
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }


}

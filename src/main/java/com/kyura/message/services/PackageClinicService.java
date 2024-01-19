package com.kyura.message.services;

import com.kyura.message.models.PackageClinic;
import com.kyura.message.payload.request.PackageRequest;
import com.kyura.message.payload.request.UpdatePackageClinicRequest;

import java.util.List;

public interface PackageClinicService {
    List<PackageClinic> getAllPackage();
    Long addPackage(PackageRequest packageRequest);
    Long updatePackageClinic(UpdatePackageClinicRequest updatePackageClinicRequest);
    PackageClinic findByid(Long id);
}

package com.kyura.message.services.impl;

import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.exception.BadRequestException;
import com.kyura.message.models.PackageClinic;
import com.kyura.message.models.Specialists;
import com.kyura.message.payload.request.PackageRequest;
import com.kyura.message.payload.request.UpdatePackageClinicRequest;
import com.kyura.message.repository.PackageClinicRepository;
import com.kyura.message.repository.SpecialistRepository;
import com.kyura.message.services.PackageClinicService;
import com.kyura.message.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageClinicServiceImpl implements PackageClinicService {
    @Autowired
    private PackageClinicRepository packageClinicRepository;
    @Autowired
    private SpecialistRepository specialistRepository;
    @Override
    public List<PackageClinic> getAllPackage() {
        return packageClinicRepository.findAll();
    }

    @Override
    public Long addPackage(PackageRequest packageRequest) {
        PackageClinic pack = new PackageClinic();
        pack.setName(packageRequest.getName());
        pack.setDescription(packageRequest.getDescription());
        pack.setThumbnail(packageRequest.getThumbnail());
        pack.setPrice(packageRequest.getPrice());
        pack.setAge(packageRequest.getAge());
        pack.setGender(packageRequest.getGender());
        pack.setStatus(ACTIVE_STATUS.ACTIVE);
        Specialists specialists = specialistRepository.findById(packageRequest.getSpecialistId());
        if(specialists == null){
            throw new BadRequestException(Constant.ERR_PACKAGE_NOT_FOUND);
        }
        pack.setSpecialists(specialists);
        packageClinicRepository.save(pack);
        return pack.getId();
    }

    @Override
    public Long updatePackageClinic(UpdatePackageClinicRequest updatePackageClinicRequest) {
        PackageClinic pack = getPackage(updatePackageClinicRequest.getId());

        pack.setId(updatePackageClinicRequest.getId());
        pack.setName(updatePackageClinicRequest.getName());
        pack.setDescription(updatePackageClinicRequest.getDescription());
        pack.setPrice(updatePackageClinicRequest.getPrice());
        pack.setGender(updatePackageClinicRequest.getGender());
        pack.setAge(updatePackageClinicRequest.getAge());

        Specialists specialists = specialistRepository.findById(updatePackageClinicRequest.getSpecialistsId());
        if (specialists==null){
            throw new BadRequestException(Constant.ERR_SPECIALIST_NOT_FOUND);
        }
        pack.setSpecialists(specialists);
        pack.setStatus(updatePackageClinicRequest.getStatus());

        packageClinicRepository.save(pack);

        return pack.getId();
    }

    @Override
    public PackageClinic findByid(Long id) {
        return getPackage(id);
    }

    private PackageClinic getPackage(Long id){
        return packageClinicRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(Constant.ERR_PACKAGE_NOT_FOUND));
    }
}

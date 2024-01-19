package com.kyura.message.services.impl;

import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.exception.BadRequestException;
import com.kyura.message.models.Doctors;
import com.kyura.message.models.Hospitals;
import com.kyura.message.models.Specialists;
import com.kyura.message.models.User;
import com.kyura.message.payload.request.DoctorsRequest;
import com.kyura.message.payload.request.ForgotPasswordRequest;
import com.kyura.message.payload.request.SignupRequest;

import com.kyura.message.payload.response.DoctorBySpecialtyReponse;

import com.kyura.message.payload.request.UpdateDoctorsRequest;

import com.kyura.message.payload.response.DoctorsResponse;
import com.kyura.message.payload.response.UserOnlyReponse;
import com.kyura.message.repository.DoctorsRepository;
import com.kyura.message.repository.HospitalRepository;
import com.kyura.message.repository.SpecialistRepository;
import com.kyura.message.repository.UserRepository;
import com.kyura.message.services.DoctorsService;
import com.kyura.message.services.UserService;
import com.kyura.message.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DoctorsServiceImpl implements DoctorsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorsRepository doctorsRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private SpecialistRepository specialistRepository;

    @Override
    public List<DoctorsResponse> findAll() {
        List<Doctors> doctors = doctorsRepository.findAll();
        List<DoctorsResponse> doctorsResponseList = new ArrayList<>();
        for (Doctors item : doctors) {
            DoctorsResponse doctorsResponse = new DoctorsResponse();
            doctorsResponse.setId(item.getId());
            doctorsResponse.setUserId(item.getId());
            doctorsResponse.setSpecialityId(item.getSpecialityId());

//            doctorsResponse.setSchedules(item.getSchedules().getStart_time());
//            doctorsResponse.setSchedules(item.getSchedules().getEnd_time());

            doctorsResponse.setFullNameDoctor(item.getUser().getFullname());
            doctorsResponse.setPhoneNumber(item.getUser().getPhone());
            doctorsResponse.setGender(item.getUser().getGender());
            doctorsResponse.setDate_of_birth(item.getUser().getDate_of_birth());

            doctorsResponse.setHospitalsName(item.getHospitals().getName());
            doctorsResponse.setAddress(item.getHospitals().getAddress());

            doctorsResponseList.add(doctorsResponse);
        }
        return doctorsResponseList;
    }

    @Override
    public Long addDoctor(DoctorsRequest doctorsRequest) {

        Optional<User> user = userRepository.findByUsernameAndStatus(doctorsRequest.getUsername(), ACTIVE_STATUS.ACTIVE);
        if (!user.isPresent()) {
            throw new BadRequestException(Constant.ERR_USER_NOT_FOUND);
        }
        Doctors doctors = new Doctors();
        doctors.setDescription(doctorsRequest.getDesc());
        doctors.setUser(user.get());
        Optional<Hospitals> hospitals = hospitalRepository.findById(doctorsRequest.getHospitalsId());
        if (!hospitals.isPresent()) {
            throw new BadRequestException(Constant.ERR_HOSPITAL_NOT_FOUND);
        }
        doctors.setHospitals(hospitals.get());
        Set<Specialists> specialistsSet = new HashSet<>();
        Specialists specialists = specialistRepository.findById(doctorsRequest.getSpecialityId());
        specialistsSet.add(specialists);
        doctors.setSpecialityId(specialistsSet);

        doctorsRepository.save(doctors);
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail(doctorsRequest.getEmail());
        try {
            userService.forgotPassword(forgotPasswordRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during forgotPassword", e);
        }
        return doctors.getId();
    }

    @Override
    public List<DoctorBySpecialtyReponse> searchDoctorsBySpecialtyId(Long specialityId) {
        List<Doctors> doctors = doctorsRepository.findBySpecialityId_Id(specialityId);
        List<DoctorBySpecialtyReponse> doctorBySpecialtyReponsesList = new ArrayList<>();
        for (Doctors item : doctors) {
            DoctorBySpecialtyReponse doctorBySpecialtyReponses = new DoctorBySpecialtyReponse();
            doctorBySpecialtyReponses.setId(item.getId());
            doctorBySpecialtyReponses.setFullName(item.getUser().getFullname());
            doctorBySpecialtyReponses.setPhone(item.getUser().getPhone());
            doctorBySpecialtyReponses.setDescription(item.getDescription());
            doctorBySpecialtyReponses.setNameHospital(item.getHospitals().getName());

            doctorBySpecialtyReponsesList.add(doctorBySpecialtyReponses);
        }

        return doctorBySpecialtyReponsesList;
    }

    @Override
    public Long updateDoctor(UpdateDoctorsRequest updateDoctorsRequest) {
        Doctors doctors = getDoctors(updateDoctorsRequest.getId());

        doctors.setId(updateDoctorsRequest.getId());
        doctors.setDescription(updateDoctorsRequest.getDesc());

        doctors.getUser().setFullname(updateDoctorsRequest.getFullname());
        doctors.getUser().setEmail(updateDoctorsRequest.getEmail());
        doctors.getUser().setPhone(updateDoctorsRequest.getPhone());
        doctors.getUser().setDate_of_birth(updateDoctorsRequest.getDate_of_birth());
        doctors.getUser().setGender(updateDoctorsRequest.getGender());
//    doctors.getUser().setAvatar(updateDoctorsRequest.getAvatar());

        doctorsRepository.save(doctors);

        return doctors.getId();
    }

    @Override
    public Doctors findByid(Long id) {
//    Doctors doctors = getDoctors(id);
        return getDoctors(id);
    }

    private Doctors getDoctors(Long id) {
        return doctorsRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(Constant.ERR_DOCTOR_NOT_FOUND));
    }

}

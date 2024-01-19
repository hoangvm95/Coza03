package com.kyura.message.services.impl;

import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.exception.BadRequestException;
import com.kyura.message.models.*;
import com.kyura.message.payload.request.ForgotPasswordRequest;
import com.kyura.message.payload.request.HospitalRequest;
import com.kyura.message.payload.request.SignupRequest;
import com.kyura.message.payload.request.UpdateHospitalRequest;
import com.kyura.message.payload.response.HospitalResponse;
import com.kyura.message.payload.response.JwtResponse;
import com.kyura.message.payload.response.MessageResponse;
import com.kyura.message.repository.*;
import com.kyura.message.services.HospitalService;
import com.kyura.message.services.ScheduleService;
import com.kyura.message.services.UserService;
import com.kyura.message.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SpecialistRepository specialistRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleDetailRepository scheduleDetailRepository;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private UserService userService;
    @Override
    public List<Hospitals> findAll() {
        return hospitalRepository.findAll();    }

    @Override
    public Hospitals findById(long id) {
        return hospitalRepository.findById(id).orElseThrow(() -> new BadRequestException(Constant.ERR_HOSPITAL_NOT_FOUND));
    }
    @Transactional
    @Override
    public ResponseEntity<?> addHospital(HospitalRequest hospitalRequest) {
        Hospitals hospitals = new Hospitals();
        hospitals.setName(hospitalRequest.getName());
        hospitals.setStatus(ACTIVE_STATUS.ACTIVE);
        hospitals.setAddress(hospitalRequest.getAddress());
        hospitals.setDesciption(hospitalRequest.getDesciption());
        SignupRequest signupRequest = new SignupRequest(hospitalRequest.getEmail(), hospitalRequest.getPhone(), hospitalRequest.getRole(), hospitalRequest.getPassword(), hospitalRequest.getUsername());
        userService.register(signupRequest);
        Optional<User> userOptional = userRepository.findByUsernameAndStatus(hospitalRequest.getUsername(), ACTIVE_STATUS.ACTIVE);

        if (!userOptional.isPresent()) {
            throw new BadRequestException(Constant.ERR_USER_NOT_FOUND);
        }

        User user = userOptional.get();
        hospitals.setUser(user);
        hospitals.setCreatedBy("Admin");
        Set<Specialists> specialistsSet = new HashSet<>();
        List<Specialists> specialistsList = specialistRepository.findAll();
        for(Specialists specialists : specialistsList){
            specialistsSet.add(specialists);
        }
        hospitals.setSpecialists(specialistsSet);
        hospitals = hospitalRepository.save(hospitals);
        Schedules schedules = new Schedules();
        schedules.setHospitals(hospitals);
        schedules.setSchedules_date(hospitalRequest.getSchedules_date());
        schedules.setStart_time(hospitalRequest.getStart_time());
        schedules.setEnd_time(hospitalRequest.getEnd_time());
        scheduleRepository.save(schedules);
        scheduleService.handleSchedule(schedules.getId());
        hospitals.setSchedules(schedules);
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail(hospitalRequest.getEmail());
        try {
            userService.forgotPassword(forgotPasswordRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during forgotPassword", e);
        }
        return ResponseEntity.ok(new MessageResponse<>(Constant.SUCCESS, new HospitalResponse(hospitals.getId(), hospitals.getName(), hospitals.getDesciption(), hospitals.getAddress(), hospitals.getStatus(), hospitals.getUser().getUsername(), hospitals.getSchedules(), hospitals.getSpecialists() )));
    }
    @Transactional
    @Override
    public ResponseEntity<?> updateHospital(UpdateHospitalRequest hospitalRequest) {
        Optional<Hospitals> hospitalsOptional = hospitalRepository.findById(hospitalRequest.getId());
        if(!hospitalsOptional.isPresent()){
            throw new BadRequestException(Constant.ERR_HOSPITAL_NOT_FOUND);
        }
        Hospitals hospitals = hospitalsOptional.get();
        hospitals.setName(hospitalRequest.getName());
        hospitals.setStatus(ACTIVE_STATUS.ACTIVE);
        hospitals.setAddress(hospitalRequest.getAddress());
        hospitals.setDesciption(hospitalRequest.getDesciption());
        Optional<User> userOptional = userRepository.findByUsernameAndStatus(hospitalRequest.getUsername(), ACTIVE_STATUS.ACTIVE);

        if (!userOptional.isPresent()) {
            throw new BadRequestException(Constant.ERR_USER_NOT_FOUND);
        }

        User user = userOptional.get();
        hospitals.setUser(user);
        hospitals.setCreatedBy("Admin");
        hospitals = hospitalRepository.save(hospitals);
        if(hospitals.getSchedules() == null && hospitalRequest.getSchedules_date() != null){
            Schedules schedules = new Schedules();
            schedules.setHospitals(hospitals);
            schedules.setSchedules_date(hospitalRequest.getSchedules_date());
            schedules.setStart_time(hospitalRequest.getStart_time());
            schedules.setEnd_time(hospitalRequest.getEnd_time());
            scheduleRepository.save(schedules);
            scheduleService.handleSchedule(schedules.getId());
            hospitals.setSchedules(schedules);
        }
        else{
            scheduleDetailRepository.deleteAllBySchedules(hospitals.getSchedules());
            scheduleRepository.deleteAllByHospitals(hospitals);
            Schedules schedules = new Schedules();
            schedules.setHospitals(hospitals);
            schedules.setSchedules_date(hospitalRequest.getSchedules_date());
            schedules.setStart_time(hospitalRequest.getStart_time());
            schedules.setEnd_time(hospitalRequest.getEnd_time());
            scheduleRepository.save(schedules);
            scheduleService.handleSchedule(schedules.getId());
            hospitals.setSchedules(schedules);
        }
        return ResponseEntity.ok(new MessageResponse<>(Constant.SUCCESS, new HospitalResponse(hospitals.getId(), hospitals.getName(), hospitals.getDesciption(), hospitals.getAddress(), hospitals.getStatus(), hospitals.getUser().getUsername(), hospitals.getSchedules(), hospitals.getSpecialists() )));
    }
}

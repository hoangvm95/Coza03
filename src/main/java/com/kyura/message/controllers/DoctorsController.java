package com.kyura.message.controllers;

import com.kyura.message.exception.BadRequestException;
import com.kyura.message.models.Doctors;
import com.kyura.message.payload.request.DoctorsRequest;
import com.kyura.message.payload.request.UpdateDoctorsRequest;
import com.kyura.message.payload.response.BaseResponse;
import com.kyura.message.payload.response.DoctorBySpecialtyReponse;
import com.kyura.message.services.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorsController {

    @Value("${root.file.path}")
    private String rootPath;

    @Autowired
    private DoctorsService doctorsService;
    @GetMapping
    public ResponseEntity<?> getAllDoctors(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(doctorsService.findAll());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/addDoctor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addDoctor(@RequestBody DoctorsRequest doctorsRequest){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(doctorsService.addDoctor(doctorsRequest));
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }


    @GetMapping("/specialty/{specialtyId}")
    public List<DoctorBySpecialtyReponse> getDoctorsBySpecialty(@PathVariable Long specialtyId) {
        return doctorsService.searchDoctorsBySpecialtyId(specialtyId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable long id){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(doctorsService.findByid(id));
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @PutMapping("/updateDoctor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateDoctor(@RequestBody UpdateDoctorsRequest updateDoctorsRequest){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setData(doctorsService.updateDoctor(updateDoctorsRequest));
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        //Uploadfile
//    @PostMapping("")
//    public ResponseEntity<?> addAvatarDoctor(@Valid DoctorsRequest doctorsRequest){
//        String filename = doctorsRequest.getFile().getOriginalFilename();
//        try {
//            String rootFolder = rootPath;
//            Path pathRoot = Paths.get(rootFolder);
//            if (!Files.exists(pathRoot)) {
//                Files.createDirectory(pathRoot);
//            }
//            Files.copy(doctorsRequest.getFile().getInputStream(), pathRoot.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
//            doctorsService.addDoctor(doctorsRequest);
//        }catch (Exception e){
//        }
//        return new ResponseEntity<>(filename,HttpStatus.OK);
//    }
//    @GetMapping("/file/{filename}")
//    public ResponseEntity<?> downloadFileImage(@PathVariable String filename) throws FileNotFoundException {
//        try {
//            // Định nghĩa đường dẫn folder để lưu file
//            Path path = Paths.get(rootPath);
//
//            Path pathFile = path.resolve(filename);
//            Resource resource = new UrlResource(pathFile.toUri());
//            if (resource.exists() || resource.isReadable()){
//                // Cho phép download file
//                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                        .body(resource);
//            }else {
//                throw new FileNotFoundException("Không tìm thấy file");
////                throw new RuntimeException("Không tìm thấy file");
//            }
//        }catch(Exception e){
//            throw new FileNotFoundException ("Không tìm thấy file");
//        }
//    }

}

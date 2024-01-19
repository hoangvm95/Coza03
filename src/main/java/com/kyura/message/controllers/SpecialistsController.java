package com.kyura.message.controllers;

import com.kyura.message.models.Specialists;
import com.kyura.message.payload.response.BaseResponse;
import com.kyura.message.services.SpecialistsService;
import com.kyura.message.services.impl.SpecialistsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/specialists")
public class SpecialistsController {
    @Autowired
    private SpecialistsService specialistsService;

    @GetMapping("")
    public ResponseEntity<?> getAllSpecialists(){
        BaseResponse response = new BaseResponse();
        response.setData(specialistsService.getAllSpecialists());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllById(@PathVariable Long id){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(specialistsService.findById(id));
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

}

package com.cybersoft.cozastore03.controller;

import com.cybersoft.cozastore03.payload.response.BaseResponse;
import com.cybersoft.cozastore03.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getCategory(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(categoryServiceImp.getAllCategory());

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}

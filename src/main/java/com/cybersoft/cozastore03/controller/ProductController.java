package com.cybersoft.cozastore03.controller;

import com.cybersoft.cozastore03.dto.ProductDTO;
import com.cybersoft.cozastore03.exception.ProductNotFoundException;
import com.cybersoft.cozastore03.payload.response.BaseResponse;
import com.cybersoft.cozastore03.service.imp.FileServiceImp;
import com.cybersoft.cozastore03.service.imp.ProductServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//Spring Batch, Apache POI, iText5
//masterdata
@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImp productServiceImp;

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("")
    public ResponseEntity<?> getAllProduct(){
        List<ProductDTO> list = productServiceImp.getAllProduct();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(list);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
//Nhớ demo @Transaction
//
//Java 8
    @PostMapping("")
    public ResponseEntity<?> insertProduct(
            @RequestParam MultipartFile file,
            @RequestParam String title,
            @RequestParam double price,
            @RequestParam int idCategory,
            @RequestParam(required = false) String tag
    ){
        productServiceImp.save(file,title,price,idCategory,tag);

        return new ResponseEntity<>("Hello insert", HttpStatus.OK);
    }

}

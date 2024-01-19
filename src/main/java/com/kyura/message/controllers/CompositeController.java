package com.kyura.message.controllers;

import com.kyura.message.payload.request.CompositeRequest;
import com.kyura.message.payload.response.BaseResponse;
import com.kyura.message.services.CompositeService;
import com.kyura.message.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@RestController
@RequestMapping("/api/composite")
public class CompositeController {

    @Autowired
    private FileService fileService;
    @Autowired
    private CompositeService compositeService;

    @PostMapping("")
    public ResponseEntity<?> uploadFile(@Valid CompositeRequest compositeRequest) {
        fileService.save(compositeRequest);
        compositeService.saveImage(compositeRequest);
        return new ResponseEntity<>("Luu database", HttpStatus.OK);
    }

    @GetMapping("/file/{filename}")
    public ResponseEntity<?> loadFile(@PathVariable String filename){
        Resource resource = fileService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

}

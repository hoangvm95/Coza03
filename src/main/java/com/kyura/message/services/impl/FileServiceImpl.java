package com.kyura.message.services.impl;

import com.kyura.message.payload.request.CompositeRequest;
import com.kyura.message.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileServiceImpl implements FileService {
    @Value("${root.file.path}")
    private String root;

    @Override
    public void save(CompositeRequest compositeRequest) {
        Path rootPath = Paths.get(root);
        try{
            if(!Files.exists(rootPath)){
                Files.createDirectories(rootPath);
            }
            Files.copy(compositeRequest.getFile().getInputStream(),rootPath.resolve(Objects.requireNonNull(compositeRequest.getFile().getOriginalFilename())), StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            throw new RuntimeException("Loi upload file " + e.getMessage());
        }
    }

    public Resource load(String fileName) {
        try{
            Path pathImage = Paths.get(root).resolve(fileName);
            Resource resource = new UrlResource(pathImage.toUri());
            if(resource.exists() || resource.isReadable()){
                return  resource;
            }else{
                throw new RuntimeException("Lỗi không tìm thấy file hoặc không đọc được file");
            }
        }catch (Exception e){
            throw new RuntimeException("Lỗi không tìm thấy file " + e.getMessage());
        }

    }
}

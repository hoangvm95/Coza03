package com.cybersoft.cozastore03.service;

import com.cybersoft.cozastore03.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService implements FileServiceImp {

    @Value("${root.path.upload}")
    private String root;

    @Override
    public void save(MultipartFile file) {
        Path rootPath = Paths.get(root);
        try{
            if(!Files.exists(rootPath)){
                Files.createDirectories(rootPath);
            }
            Files.copy(file.getInputStream(),rootPath.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            throw new RuntimeException("Loi upload file " + e.getMessage());
        }
    }

    @Override
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

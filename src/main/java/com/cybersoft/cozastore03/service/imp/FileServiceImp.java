package com.cybersoft.cozastore03.service.imp;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceImp {
    void save(MultipartFile file);
    Resource load(String fileName);
}

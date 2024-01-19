package com.cybersoft.cozastore03.service.imp;

import com.cybersoft.cozastore03.dto.ProductDTO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductServiceImp {
    List<ProductDTO> getAllProduct();
    void save( MultipartFile file, String title, double price, int idCategory, String tag);
}

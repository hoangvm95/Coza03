package com.kyura.message.services.impl;

import com.kyura.message.models.CompositeId;
import com.kyura.message.payload.request.CompositeRequest;
import com.kyura.message.repository.CompositeRepository;
import com.kyura.message.services.CompositeService;
import com.kyura.message.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class CompositeServiceImpl implements CompositeService {

    @Value("${root.file.path}")
    private String root;
   @Autowired
    private FileService fileService;
   @Autowired
   private CompositeRepository compositeRepository;

    @Override
    public void saveImage(CompositeRequest compositeRequest) {
        CompositeId compositeId = new CompositeId();
        compositeId.setImage(root+ "\\" + compositeRequest.getFile().getOriginalFilename());
        compositeId.setIdType(compositeRequest.getIdType());
        compositeId.setIdTarget(compositeRequest.getIdTarget());
        compositeRepository.save(compositeId);
    }
}

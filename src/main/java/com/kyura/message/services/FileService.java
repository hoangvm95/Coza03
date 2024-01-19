package com.kyura.message.services;

import com.kyura.message.payload.request.CompositeRequest;
import org.springframework.core.io.Resource;


public interface FileService {
    void save(CompositeRequest compositeRequest);

    Resource load (String filename);

}

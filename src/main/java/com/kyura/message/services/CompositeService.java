package com.kyura.message.services;

import com.kyura.message.payload.request.CompositeRequest;

public interface CompositeService {
    void saveImage(CompositeRequest compositeRequest);
}

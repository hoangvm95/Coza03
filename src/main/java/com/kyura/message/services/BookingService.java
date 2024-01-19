package com.kyura.message.services;


import com.kyura.message.payload.response.BookingReponse;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    List<BookingReponse> findAll();

    List<BookingReponse> findById(Long id);
}

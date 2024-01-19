package com.kyura.message.controllers;
import com.kyura.message.payload.response.BookingReponse;
import com.kyura.message.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/all")
    List<BookingReponse> findAll(){
      return bookingService.findAll();
    }

    @GetMapping("/{id}")
    List<BookingReponse> findById(@PathVariable Long id){
        return bookingService.findById(id);
    }

}
